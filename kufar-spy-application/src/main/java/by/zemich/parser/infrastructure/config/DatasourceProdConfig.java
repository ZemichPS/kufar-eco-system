package by.zemich.parser.infrastructure.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "by.zemich.parser.infrastructure.repository.jparepository")
public class DatasourceProdConfig {


    @Profile("test")
    @Bean
    DataSource testDataSource(){
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.postgresql.Driver");
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/test_kufar_parser");
        config.setUsername("postgres");
        config.setPassword("postgres");
        config.setSchema("app");
        return new HikariDataSource(config);
    }
}
