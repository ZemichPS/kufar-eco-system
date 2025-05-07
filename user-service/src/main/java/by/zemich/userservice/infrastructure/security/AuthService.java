package by.zemich.userservice.infrastructure.security;

import by.zemich.userservice.infrastructure.security.entities.UserDetailsImpl;
import by.zemich.userservice.infrastructure.security.repository.UserDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsRepository userDetailsRepository;
    private final JwtService jwtService;

    public String login(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        UserDetailsImpl foundedUser = userDetailsRepository.findByEmail(email).orElseThrow();
        return jwtService.generateToken(foundedUser);
    }

    public void changePassword(String email, String password, String newPassword) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        UserDetailsImpl user = userDetailsRepository.findByEmail(email).orElseThrow();
        user.setPassword(newPassword);
        userDetailsRepository.save(user);
    }
}
