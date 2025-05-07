package by.zemich.userservice.infrastructure.security.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(schema = "app", name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {
    @Id
    private String email;

    @Getter
    private UUID id;

    private String firstName;

    private String lastName;

    private String username;

    private LocalDateTime registrationDate;

    private boolean enabled;

    @OneToMany(
            mappedBy = "userDetails",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ExternalIdentity> externalIdentities;

    @Setter
    private String password;

    @Getter
    private String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public void addExternalIdentity(ExternalIdentity externalIdentity) {
        externalIdentity.setUserDetails(this);
        externalIdentities.add(externalIdentity);
    }

}
