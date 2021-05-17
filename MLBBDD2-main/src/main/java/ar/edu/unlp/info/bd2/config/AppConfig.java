package ar.edu.unlp.info.bd2.config;

import ar.edu.unlp.info.bd2.repositories.MLRepository;
import ar.edu.unlp.info.bd2.repositories.MLRepositoryStatistics;
import ar.edu.unlp.info.bd2.services.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public MLService createService() {
        MLRepository repository = this.createRepository();
        MLRepositoryStatistics repositoryStatistics = this.createRepositoryStatistics();
        return new MLServiceImpl(repository, repositoryStatistics);
    }

    @Bean
    public MLRepository createRepository() {
        return new MLRepository();
    }
    
    @Bean
    public MLRepositoryStatistics createRepositoryStatistics() {
        return new MLRepositoryStatistics();
    }
}
