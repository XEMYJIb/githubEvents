package com.petproject.service;

import com.petproject.configuration.PropertiesProvider;
import com.petproject.exception.ApplicationRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestOperations;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Optional;

/**
 * @author anton.demus
 * @since 2019-08-05
 */
@Service
public class RestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestService.class);

    private final RestOperations restOperations;
    private final MultiValueMap<String, String> gitHubDefaultHeaders;

    @Inject
    public RestService(RestOperations restOperations,
                       PropertiesProvider propertiesProvider) {
        this.restOperations = restOperations;
        final HttpHeaders defaultHeaders = new HttpHeaders();
        defaultHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        defaultHeaders.set("Authorization", "token " + propertiesProvider.getGithubToken());
        this.gitHubDefaultHeaders = defaultHeaders;
    }

    public <T> Optional<T> getFromGitHub(String url, Class<T> responseType) {
        try {
            final HttpEntity<String> entity = new HttpEntity<>("body", gitHubDefaultHeaders);
            return Optional.ofNullable(restOperations.exchange(url, HttpMethod.GET, entity, responseType).getBody());
        } catch (RestClientResponseException ex) {
            LOGGER.error("Cannot get object {} by rest", responseType.getTypeName());
            throw new ApplicationRuntimeException(ex);
        }
    }
}
