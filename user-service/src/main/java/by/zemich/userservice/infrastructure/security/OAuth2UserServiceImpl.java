package by.zemich.userservice.infrastructure.security;

import by.zemich.userservice.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OAuth2UserServiceImpl implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserDetailsRepository detailsRepository;
    private final JwtService jwtService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        var delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        String email = oAuth2User.getAttribute("email");

        UserDetailsImpl details = detailsRepository.findByEmail(email)
                .orElse(UserDetailsImpl.builder()
                        .id(UUID.randomUUID())
                        .email(email)
                        .role("USER")
                        .build());

        String jwt = jwtService.generateToken(details);
        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());

        attributes.put("jwt", jwt);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(details.getRole())),
                attributes,
                "email"
        );
    }
}
