package pl.mateusz_semklo.automationshoprest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
     @Override
       public void addCorsMappings(CorsRegistry corsRegistry) {
         corsRegistry.addMapping("/**")
                 .allowedOrigins("*")
                 .allowedMethods("*")
                 .maxAge(3600L)
                 .allowedHeaders("*");
     }

}
