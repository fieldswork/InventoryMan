package com.skillstorm.inventoryman;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(
                            "http://localhost:3000", // Local development
                            "http://44.214.65.155",  // AWS Elastic IP for backend
                            "inventoryman-env.us-east-1.elasticbeanstalk.com", // EB URL
                            "http://18.209.149.218", // EC2 IP 
                            "http://inventoryman.s3-website-us-east-1.amazonaws.com" // Frontend S3 bucket
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*");
            }
        };
    }
}
