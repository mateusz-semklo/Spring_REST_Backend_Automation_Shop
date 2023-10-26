package pl.mateusz_semklo.automationshoprest;

import jakarta.servlet.Servlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.CrossOrigin;


@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
@EnableConfigurationProperties
@ConfigurationPropertiesScan
public class AutomationShopRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutomationShopRestApplication.class, args);
	}

}
