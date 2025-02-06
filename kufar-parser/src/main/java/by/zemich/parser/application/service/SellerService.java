package by.zemich.parser.application.service;

import by.zemich.parser.domain.model.Seller;
import by.zemich.parser.infrastructure.repository.jparepository.SellerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SellerService {
    private final SellerRepository sellerRepository;

    public Seller save(Seller seller) {
        return sellerRepository.save(seller);
    }

    public Optional<Seller> getById(String sellerId) {
        return sellerRepository.findById(sellerId);
    }

}
