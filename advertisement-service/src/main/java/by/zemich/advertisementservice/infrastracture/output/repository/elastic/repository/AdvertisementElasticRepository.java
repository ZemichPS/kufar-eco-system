package by.zemich.advertisementservice.infrastracture.output.repository.elastic.repository;

import by.zemich.advertisementservice.domain.dto.FullAdvertisementDto;
import by.zemich.advertisementservice.domain.repository.AdvertisementFullTextQueryRepository;
import by.zemich.advertisementservice.infrastracture.output.repository.elastic.documents.AdvertisementDocument;
import by.zemich.advertisementservice.infrastracture.output.repository.elastic.mapper.AdvertisementMapper;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.api.AdvertisementRepository;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity.AdvertisementEntity;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdvertisementElasticRepository implements AdvertisementFullTextQueryRepository {

    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public Page<FullAdvertisementDto> fullTextSearch(String query, Pageable pageable) {

        NativeQuery searchQuery = NativeQuery.builder()
                .withQuery(q -> q
                        .match(m -> m
                                .field("categoryName")
                                .query(query)
                        )
                )
                .withPageable(pageable)
                .build();

        SearchHits<AdvertisementDocument> hits = elasticsearchOperations.search(searchQuery, AdvertisementDocument.class);
        List<FullAdvertisementDto> dtos = hits
                .map(SearchHit::getContent)
                .map(AdvertisementMapper::map)
                .toList();

        return new PageImpl<>(dtos, pageable, hits.getTotalHits());
    }
}
