package by.zemich.telegrambotservice.application.service.scenarious.api;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public interface RoleProvider {

    default List<String> getRoles(String filePath){
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            return br.lines().collect(Collectors.toList());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
