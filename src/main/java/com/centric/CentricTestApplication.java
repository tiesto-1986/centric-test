package com.centric;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@EntityScan(basePackages = { "com.centric.entity" })
@EnableJpaRepositories(basePackages = { "com.centric.repository" })
@SpringBootApplication
public class CentricTestApplication {
    
    public static void main( String[] args ) {
        SpringApplication.run(CentricTestApplication.class, args);
    }
    
}
