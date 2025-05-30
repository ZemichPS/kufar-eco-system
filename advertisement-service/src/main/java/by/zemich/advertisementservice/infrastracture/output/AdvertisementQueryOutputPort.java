package by.zemich.advertisementservice.infrastracture.output;

import by.zemich.advertisementservice.domain.dto.AdvertisementFilter;
import by.zemich.advertisementservice.domain.dto.FullAdvertisementDto;
import by.zemich.advertisementservice.domain.repository.AdvertisementFullTextQueryRepository;
import by.zemich.advertisementservice.domain.repository.AdvertisementQueryRepository;
import by.zemich.advertisementservice.domain.valueobject.AdvertisementId;
import by.zemich.advertisementservice.infrastracture.output.repository.elastic.documents.AdvertisementDocument;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.api.AdvertisementRepository;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity.AdvertisementEntity;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.mapper.AdvertisementMapper;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.specifications.AdvertisementSpecificationFilter;
import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@CacheConfig(
        cacheManager = "advertisementCaffeineCacheManager"
)
public class AdvertisementQueryOutputPort implements AdvertisementQueryRepository, AdvertisementFullTextQueryRepository {

    private final AdvertisementRepository advertisementRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "advertisementsForQueryCache", key = "#id.uuid()")
    public Optional<FullAdvertisementDto> getFullResponseById(AdvertisementId id) {
        UUID advertisemntUuid = id.uuid();
        return advertisementRepository
                .findById(advertisemntUuid)
                .map(AdvertisementMapper::mapToDto);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "advertisementsForQueryCache", keyGenerator = "advertisementQueryKeyGenerator")
    public Page<FullAdvertisementDto> getPage(AdvertisementFilter filter, Pageable pageable) {
        AdvertisementSpecificationFilter specificationFilter = toSpecificationFilter(filter);
        Page<AdvertisementEntity> entityPage = advertisementRepository.findAll(specificationFilter.buildSpecification(), pageable);
        return entityPage.map(AdvertisementMapper::mapToDto);
    }

    @Override
    public Page<FullAdvertisementDto> fullTextSearch(String query, Pageable pageable) {
        NativeQuery searchQuery = createNativeQuery(query, pageable);

        SearchHits<AdvertisementDocument> hits = elasticsearchOperations.search(searchQuery, AdvertisementDocument.class);
        List<FullAdvertisementDto> fullAdvertisementDtoList = hits
                .map(SearchHit::getContent)
                .map(AdvertisementDocument::getUuid)
                .map(advertisementRepository::findById)
                .flatMap(Optional::stream)
                .map(AdvertisementMapper::mapToDto)
                .toList();

        return new PageImpl<>(fullAdvertisementDtoList, pageable, hits.getTotalHits());
    }

    private AdvertisementSpecificationFilter toSpecificationFilter(AdvertisementFilter filter) {
        return AdvertisementSpecificationFilter.builder()
                .active(filter.getActive())
                .side(Optional.ofNullable(filter.getSide())
                        .map(value -> AdvertisementSpecificationFilter.Side.valueOf(value.name()))
                        .orElse(null))
                .categoryName(filter.getCategoryName())
                .priceFrom(filter.getPriceFrom())
                .priceTo(filter.getPriceTo())
                .userId(Objects.nonNull(filter.getUserId()) ? filter.getUserId().uuid() : null)
                .publishedAt(filter.getPublishedAt())
                .condition(Optional.ofNullable(filter.getCondition())
                        .map(value -> AdvertisementSpecificationFilter.Condition.valueOf(value.name()))
                        .orElse(null))
                .build();
    }

    private NativeQuery createNativeQuery(String query, Pageable pageable) {
        return NativeQuery.builder()
                .withQuery(q -> q
                        .multiMatch(m -> m
                                .fields("categoryName", "attributesValues")
                                .query(query)
                                .operator(Operator.Or)
                                .type(TextQueryType.BestFields)
                                .fuzziness("3")
                        )
                )
                .withPageable(pageable)
                .build();
    }
}
