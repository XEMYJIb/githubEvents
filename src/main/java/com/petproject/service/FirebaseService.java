package com.petproject.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.collect.ImmutableMap;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.petproject.configuration.PropertiesProvider;
import com.petproject.model.github.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author anton.demus
 * @since 2019-08-08
 */
@Service
public class FirebaseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FirebaseService.class);

    private final PropertiesProvider propertiesProvider;

    private DatabaseReference rootDatabaseReference;

    @Inject
    public FirebaseService(PropertiesProvider propertiesProvider) {
        this.propertiesProvider = propertiesProvider;
    }

    @PostConstruct
    private void init() {
        try {
            //final FileInputStream serviceAccount = new FileInputStream(propertiesProvider.getFirebasePrivateKeyPath());
            InputStream serviceAccount = new ByteArrayInputStream(propertiesProvider.getFirebasePrivateKey().getBytes(StandardCharsets.UTF_8));

            final FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl(propertiesProvider.getFirebaseDatabaseUrl())
                .build();

            FirebaseApp.initializeApp(options);
            rootDatabaseReference = FirebaseDatabase.getInstance().getReference("/");
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    public void writeGitHubDataToDB(Ticket ticket) {
        String key = ticket.getGhIssueNumber() + ":" + ticket.getGhCommentId() + ":" + ticket.getFdTicketNumber();
        DatabaseReference gitHubRef = rootDatabaseReference.child(key);

        Map<String, Object> data = new ImmutableMap.Builder<String, Object>()
            .put("issue", ticket.getGhIssueNumber())
            .put("issueLink", ticket.getIssueLink())
            .put("comment", ticket.getGhCommentId())
            .put("ticket", ticket.getFdTicketNumber())
            .put("title", ticket.getTitle())
            .put("label", ticket.getLabel())
            .put("create", ticket.getIssueCreated().toString())
            .put("update", ticket.getUpdatedDate().toString())
            .build();

        gitHubRef.setValue(data, (error, ref) -> {
            System.out.println("Done");
            //todo: Cover concurrent update, processing and resolving issues with date
        });
    }
}
