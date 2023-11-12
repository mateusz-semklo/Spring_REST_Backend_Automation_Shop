package pl.mateusz_semklo.automationshoprest.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestConfig {
    @Bean
    ModelMapper modelMapper(){
        ModelMapper mapper= new ModelMapper();
         mapper.getConfiguration().setFieldMatchingEnabled(true)
                 .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
       // .setMatchingStrategy(MatchingStrategies.LOOSE);
        return mapper;
    }
}
