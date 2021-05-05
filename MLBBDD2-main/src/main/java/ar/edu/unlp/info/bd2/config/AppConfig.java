package ar.edu.unlp.info.bd2.config;

import ar.edu.unlp.info.bd2.repositories.MLRepository;
import ar.edu.unlp.info.bd2.services.*;

import org.springframework.context.annotation.Configuration;
import ar.edu.unlp.info.bd2.services.MLServiceImpl;

@Configuration
public class AppConfig {
    
    public MLService createService() {
        MLRepository repository = this.createRepository();
        return new MLServiceImpl(repository);
    }

   
    public MLRepository createRepository() {
        return new MLRepository();
    }
}
