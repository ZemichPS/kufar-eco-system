package by.zemich.userservice.service;

import by.zemich.userservice.dao.repositories.UserRepository;
import by.zemich.userservice.service.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public String login(String email, String password) {
        return userRepository.findByEmail(email)
                .map(tokenService::generateToken)
                .orElseThrow(() -> new UserNotFoundException(email));
    }
}
