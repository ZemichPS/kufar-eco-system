package by.zemich.telegrambotservice.infrastructure.repository.filerepositories;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class ClothesBrandsFileRepository {
    private final String FILENAME = "clothes_brands.txt";

    public List<String> get() {
        try {
            var resource = Thread.currentThread().getContextClassLoader().getResource(FILENAME);
            Path path = Path.of(resource.toURI());
            return Files.readAllLines(path)
                    .stream()
                    .map(String::toLowerCase)
                    .toList();
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
