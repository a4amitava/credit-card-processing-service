package com.sapient.card.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class ServiceConfiguration {

    @Value("${api.read.time.out:30}")
    private int readTimeOut;
    @Value("${api.connect.time.out:3}")
    private int connectTimeOut;

    /**
     * default rest template timeout setting for read and connect
     *
     */
    @Bean
    RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .setReadTimeout(Duration.ofSeconds(readTimeOut))
                .setConnectTimeout(Duration.ofSeconds(connectTimeOut))
                .build();
    }
}
