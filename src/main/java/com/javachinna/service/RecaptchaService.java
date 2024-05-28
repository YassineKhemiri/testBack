package com.javachinna.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class RecaptchaService {

    private final RestTemplate restTemplate;
    private final String recaptchaSecret;

    private static final String RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    public RecaptchaService(RestTemplate restTemplate, @Value("${google.recaptcha.secret}") String recaptchaSecret) {
        this.restTemplate = restTemplate;
        this.recaptchaSecret = recaptchaSecret;
    }

    public boolean verifyRecaptcha(String recaptchaResponse) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(RECAPTCHA_VERIFY_URL)
                .queryParam("secret", recaptchaSecret)
                .queryParam("response", recaptchaResponse);

        Map<String, Object> response = restTemplate.postForObject(uriBuilder.toUriString(), null, Map.class);

        return response != null && (Boolean) response.get("success");
    }
}
