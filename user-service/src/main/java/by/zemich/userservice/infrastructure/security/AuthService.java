package by.zemich.userservice.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public String login(String email, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            return jwtTokenProvider.generateToken(authentication);
        } catch (AuthenticationException exception) {
            throw new AuthenticationServiceException(exception.getMessage());
        }
    }

    public void changePassword(String email, String password, String newPassword) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            UserEntity user = userRepository.findByEmail(email).orElseThrow();
            user.setPassword(newPassword);
            userRepository.save(user);
        } catch (AuthenticationException exception) {
            throw new AuthenticationServiceException(exception.getMessage());
        }
    }
}
