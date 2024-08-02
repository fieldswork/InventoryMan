package com.skillstorm.inventoryman;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() { // Configures CORS
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000") // Allow requests from the React app, this gets updated for the AWS deployment
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // All these methods are allowed
                        .allowedHeaders("*");
            }
        };
    }
}
