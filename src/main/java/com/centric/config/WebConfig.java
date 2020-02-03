package com.centric.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfig {

    @Bean
    public CorsFilter corsFilter() {
        List<String> data = new ArrayList<>();
        data.add("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedHeaders(data);
        config.setAllowedOrigins(data);
        config.addAllowedMethod(RequestMethod.OPTIONS.toString());
        config.addAllowedMethod(RequestMethod.GET.toString());
        config.addAllowedMethod(RequestMethod.POST.toString());
        config.addAllowedMethod(RequestMethod.PUT.toString());
        config.addAllowedMethod(RequestMethod.DELETE.toString());
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
