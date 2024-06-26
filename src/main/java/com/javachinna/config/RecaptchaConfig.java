package com.javachinna.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Getter
@Configuration
public class RecaptchaConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
