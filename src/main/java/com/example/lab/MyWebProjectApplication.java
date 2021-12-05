package com.example.lab;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;



@SpringBootApplication
public class MyWebProjectApplication {

	@Bean
	public SpringSecurityDialect securityDialect() {
		return new SpringSecurityDialect();
	}

	public static void main(String[] args) {
		SpringApplication.run(MyWebProjectApplication.class, args);
	}

}
