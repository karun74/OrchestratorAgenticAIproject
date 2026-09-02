package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = { UserDetailsServiceAutoConfiguration.class })

public class OrchestratorAgenticAIprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrchestratorAgenticAIprojectApplication.class, args);
	}

}
