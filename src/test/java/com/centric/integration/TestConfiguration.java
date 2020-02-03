package com.centric.integration;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class TestConfiguration {
    
    @Bean
    @Primary
    public DataSource dataSource() throws Exception{
    	return EmbeddedPostgres.start().getPostgresDatabase();
    }

}
