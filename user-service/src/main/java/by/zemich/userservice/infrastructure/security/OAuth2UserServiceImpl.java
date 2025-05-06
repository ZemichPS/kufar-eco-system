package by.zemich.userservice.infrastructure.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2UserServiceImpl implements OAuth2UserService<OidcUserRequest, OidcUser> {

    private final UserDetailsRepository detailsRepository;
    private final JwtService jwtService;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUserService delegate = new OidcUserService();
        OidcUser oidcUser = delegate.loadUser(userRequest);
        log.debug("User data was received.  OAuth2User: {}", oidcUser);
        String email = oidcUser.getEmail();

        if (email == null) {
            throw new OAuth2AuthenticationException("Email not found in OAuth2 provider");
        }

        UserDetailsImpl details = detailsRepository.findByEmail(email)
                .orElseGet(() -> {
                    log.debug("User has not registered yet. Try to register new user");
                    UserDetailsImpl detailsImpl = UserDetailsImpl.builder()
                            .id(UUID.randomUUID())
                            .email(email)
                            .username(oidcUser.getGivenName() + " " + oidcUser.getFamilyName())
                            .firstName(oidcUser.getGivenName())
                            .lastName(oidcUser.getFamilyName())
                            .registrationDate(LocalDateTime.now())
                            .role("USER")
                            .enabled(true)
                            .build();
                    return detailsRepository.save(detailsImpl);
                });

        return new DefaultOidcUser(
                Collections.singleton(new SimpleGrantedAuthority(details.getRole())),
                oidcUser.getIdToken(),
                oidcUser.getUserInfo(),
                "email"
        );
    }
}
