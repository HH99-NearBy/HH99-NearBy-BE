package com.hh99.nearby.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowedOrigins("http://localhost:63342","http://localhost:3000","https://thingkh-bk.shop")
                .exposedHeaders("Set-Cookie","loggedUser","authorization","Access-Token-Expire-Time","authentication")
               .allowCredentials(true);
    }
}