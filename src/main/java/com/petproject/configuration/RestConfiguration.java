package com.petproject.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestOperations;

import javax.inject.Inject;

import static java.time.Duration.ofSeconds;

@Configuration
public class RestConfiguration {

    private final PropertiesProvider propertiesProvider;

    @Inject
    public RestConfiguration(PropertiesProvider propertiesProvider) {
        this.propertiesProvider = propertiesProvider;
    }

    @Bean
    public RestOperations restOperation() {
        return new RestTemplateBuilder()
            .errorHandler(new DefaultResponseErrorHandler())
            .setConnectTimeout(ofSeconds(propertiesProvider.getRestClientConnectionTimeoutInSec()))
            .setReadTimeout(ofSeconds(propertiesProvider.getRestClientReadTimeoutInSec()))
            .build();
    }
}
