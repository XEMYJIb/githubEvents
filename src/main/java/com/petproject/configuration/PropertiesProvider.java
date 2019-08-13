package com.petproject.configuration;

import com.petproject.exception.ApplicationRuntimeException;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author anton.demus
 * @since 2019-02-28
 */
@Getter
@Service
public class PropertiesProvider {
    @Value("${rest.client.connection.timeout.in.sec: 180}")
    private int restClientConnectionTimeoutInSec;

    @Value("${rest.client.read.timeout.in.sec: 180}")
    private int restClientReadTimeoutInSec;

    @Value("${firebase.private.key.path}")
    private String firebasePrivateKeyPath;

    @Value("${firebase.private.key: }")
    private String firebasePrivateKey;

    @Value("${firebase.database.url}")
    private String firebaseDatabaseUrl;

    @Value("${github.repo.url}")
    private String githubRepoUrl;

    @Value("${github.token}")
    private String githubToken;

    @Value("${github.data.parsing.pattern: }")
    private String githubDataParsingPattern;
}
