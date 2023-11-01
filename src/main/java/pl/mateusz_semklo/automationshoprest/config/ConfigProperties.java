package pl.mateusz_semklo.automationshoprest.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties(prefix = "app.url")
@Getter
@Setter
public class ConfigProperties {

    public String serverUrl;
    public String productsUrl;
    public String categoriesUrl;
    public String ordersUrl;
    public String usersUrl;
    public String cartsUrl;

}
