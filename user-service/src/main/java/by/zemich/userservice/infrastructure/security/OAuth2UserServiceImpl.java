package by.zemich.userservice.infrastructure.security;

import by.zemich.userservice.infrastructure.security.entities.ExternalIdentity;
import by.zemich.userservice.infrastructure.security.entities.UserDetailsImpl;
import by.zemich.userservice.infrastructure.security.repository.ExternalIdentityRepository;
import by.zemich.userservice.infrastructure.security.repository.UserDetailsRepository;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2UserServiceImpl implements OAuth2UserService<OidcUserRequest, OidcUser> {

    private final UserDetailsRepository detailsRepository;
    private final ExternalIdentityRepository externalIdentityRepository;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUserService delegate = new OidcUserService();
        OidcUser oidcUser = delegate.loadUser(userRequest);
        log.debug("User data was received.  OAuth2User: {}", oidcUser);
        String email = oidcUser.getEmail();

        String providerName = userRequest.getClientRegistration().getRegistrationId();
        String providerUserId = retrieveProviderId(oidcUser, providerName);

        UserDetailsImpl details = externalIdentityRepository.findByProviderNameAndProviderUserId(providerName, providerUserId)
                .map(ExternalIdentity::getUserDetails)
                .orElseGet(() -> {
                    ExternalIdentity externalIdentity = ExternalIdentity.builder()
                            .id(UUID.randomUUID())
                            .providerName(providerName)
                            .providerUserId(providerUserId)
                            .build();

                    UserDetailsImpl userDetails = detailsRepository.findByEmail(email).orElseGet(() -> this.createUserDetailByOidcUser(oidcUser));
                    userDetails.addExternalIdentity(externalIdentity);
                    return detailsRepository.save(userDetails);
                });

        return new DefaultOidcUser(
                Collections.singleton(new SimpleGrantedAuthority(details.getRole())),
                oidcUser.getIdToken(),
                oidcUser.getUserInfo(),
                "email"
        );
    }

    private String retrieveProviderId(OidcUser oidcUser, String providerId) {
        return switch (providerId) {
            case "google", "apple" -> oidcUser.getAttribute("sub");
            case "vk", "facebook" -> oidcUser.getAttribute("id");
            default -> throw new OAuth2AuthenticationException("Unknown provider: " + providerId);
        };
    }

    private UserDetailsImpl createUserDetailByOidcUser(OidcUser oidcUser) {
        String email = oidcUser.getEmail();

        if (email == null) {
            throw new OAuth2AuthenticationException("Email not found in OAuth2 provider");
        }

        return UserDetailsImpl.builder()
                .id(UUID.randomUUID())
                .email(email)
                .username(oidcUser.getGivenName() + " " + oidcUser.getFamilyName())
                .firstName(oidcUser.getGivenName())
                .lastName(oidcUser.getFamilyName())
                .registrationDate(LocalDateTime.now())
                .externalIdentities(new ArrayList<>())
                .role("USER")
                .enabled(true)
                .build();

    }
}
