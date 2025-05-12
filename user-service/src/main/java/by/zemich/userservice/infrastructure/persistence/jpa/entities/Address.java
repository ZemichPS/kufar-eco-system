package by.zemich.userservice.infrastructure.persistence.jpa.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    private String postalCode;
    private String region;
    private String city;
    private String district;        // Район (если применимо)
    private String street;
    private String houseNumber;
    private String apartmentNumber;
}
