package by.zemich.userservice.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsRepository userDetailsRepository;
    private final JwtService jwtService;

    public String login(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (AuthenticationException exception) {
            throw new AuthenticationServiceException(exception.getMessage());
        }
        UserDetailsImpl foundedUser = userDetailsRepository.findByEmail(email).orElseThrow();
        return jwtService.generateToken(foundedUser);
    }

    public void changePassword(String email, String password, String newPassword) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (AuthenticationException exception) {
            throw new AuthenticationServiceException(exception.getMessage());
        }
        UserDetailsImpl user = userDetailsRepository.findByEmail(email).orElseThrow();
        user.setPassword(newPassword);
        userDetailsRepository.save(user);
    }
}
