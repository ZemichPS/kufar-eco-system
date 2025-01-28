package by.zemich.kufar.infrastructure.repository.jparepository;

import by.zemich.kufar.domain.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, String> {
}
