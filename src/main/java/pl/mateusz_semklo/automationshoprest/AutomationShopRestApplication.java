package pl.mateusz_semklo.automationshoprest;

import jakarta.servlet.Servlet;
import org.h2.server.web.WebServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class AutomationShopRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutomationShopRestApplication.class, args);
	}

}
