package com.containerdepot.metcon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class RestConfig {
    @Bean
    public RestClient restClient(TaskApiConfig taskApiConfig) {
        return RestClient
                .builder()
                .baseUrl(taskApiConfig.getBaseUrl())
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
