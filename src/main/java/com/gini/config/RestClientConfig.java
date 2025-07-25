package com.gini.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient weatherClient() {
        return RestClient.builder()
                .baseUrl("https://www.meteosource.com/api/v1/free")
                .build();
    }


}
