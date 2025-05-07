package by.zemich.userservice.infrastructure.security;

import by.zemich.userservice.infrastructure.security.entities.UserDetailsImpl;
import by.zemich.userservice.infrastructure.security.repository.UserDetailsRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;

@RequiredArgsConstructor
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Value("${urls.success-register-url}")
    private String redirectUrl;

    private final UserDetailsRepository detailsRepository;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        UserDetailsImpl userDetails = detailsRepository.findByEmail(email).orElseThrow();
        String jwt = jwtService.generateToken(userDetails);
        ResponseCookie cookie = ResponseCookie.from("access_token", jwt)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofDays(365))
                .sameSite("Strict")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.sendRedirect(redirectUrl);
    }
}
