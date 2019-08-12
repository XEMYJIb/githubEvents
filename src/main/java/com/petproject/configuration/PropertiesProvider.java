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
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesProvider.class);
    private static final String MANDATORY_MSG_TEMPLATE = "Property '{}' is mandatory";

    @Value("${rest.client.connection.timeout.in.sec: 180}")
    private int restClientConnectionTimeoutInSec;

    @Value("${rest.client.read.timeout.in.sec: 180}")
    private int restClientReadTimeoutInSec;

    @Value("${" + MandatoryFields.FIREBASE_PRIVATE_KEY_PATH + "}") //"/Users/anton.demus/firebase-private-key.json"
    private String firebasePrivateKeyPath;

    @Value("${" + MandatoryFields.FIREBASE_DATABASE_URL + "}") //https://rock-voice-249111.firebaseio.com
    private String firebaseDatabaseUrl;

    @Value("${" + MandatoryFields.GITHUB_REPO_URL + "}") //https://api.github.com/repos/anton-demus/Backlog_temp
    private String githubRepoUrl;

    @Value("${" + MandatoryFields.GITHUB_TOKEN + "}") //05e0f0683c07f4affa29111e266a0c5741e39bfa
    private String githubToken;

    @Value("${github.data.parsing.pattern: }") // "https://\\w+.support.teamdev.com/.+/(\\d+)"
    private String githubDataParsingPattern;

    @PostConstruct
    private void validateProperties() {
        boolean somePropertyIssue = false;

        if (StringUtils.isBlank(firebasePrivateKeyPath)) {
            LOGGER.error(MANDATORY_MSG_TEMPLATE, MandatoryFields.FIREBASE_PRIVATE_KEY_PATH);
            somePropertyIssue = true;
        }

        if (StringUtils.isBlank(firebaseDatabaseUrl)) {
            LOGGER.error(MANDATORY_MSG_TEMPLATE, MandatoryFields.FIREBASE_DATABASE_URL);
            somePropertyIssue = true;
        }

        if (StringUtils.isBlank(githubRepoUrl)) {
            LOGGER.error(MANDATORY_MSG_TEMPLATE, MandatoryFields.GITHUB_REPO_URL);
            somePropertyIssue = true;
        }

        if(StringUtils.isBlank(githubToken)) {
            LOGGER.error(MANDATORY_MSG_TEMPLATE, MandatoryFields.GITHUB_TOKEN);
            somePropertyIssue = true;
        }

        if (somePropertyIssue) {
            throw new ApplicationRuntimeException(
                "Some properties have invalid values. Please check logs for more details");
        }
    }

    private interface MandatoryFields {
        String FIREBASE_PRIVATE_KEY_PATH = "firebase.private.key.path";
        String FIREBASE_DATABASE_URL = "firebase.database.url";
        String GITHUB_REPO_URL = "github.repo.url";
        String GITHUB_TOKEN = "github.token";
    }
}
