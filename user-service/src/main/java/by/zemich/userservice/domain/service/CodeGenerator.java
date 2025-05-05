package by.zemich.userservice.domain.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CodeGenerator {
    public String generateConfirmCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }
}
