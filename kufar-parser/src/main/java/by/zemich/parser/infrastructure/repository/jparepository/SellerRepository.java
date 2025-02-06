package by.zemich.parser.infrastructure.repository.jparepository;

import by.zemich.parser.domain.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, String> {
}
