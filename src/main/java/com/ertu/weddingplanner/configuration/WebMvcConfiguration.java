package com.ertu.weddingplanner.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry
                .addMapping("/api")
                .allowedOrigins("https://elisabeth-ertugrul.netlify.app")
                .allowedMethods("GET", "POST")
                .allowedHeaders("Content-Type", "Authorization");
    }
}
