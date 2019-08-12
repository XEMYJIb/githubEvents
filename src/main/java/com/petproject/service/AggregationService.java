package com.petproject.service;

import com.petproject.model.github.Ticket;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author anton.demus
 * @since 2019-08-05
 */

@Service
public class AggregationService {
    private final GitHubService gitHubService;
    private final FreshdeskService freshdeskService;
    private final FirebaseService firebaseService;

    @Inject
    public AggregationService(GitHubService gitHubService,
                              FreshdeskService freshdeskService,
                              FirebaseService firebaseService) {
        this.gitHubService = gitHubService;
        this.freshdeskService = freshdeskService;
        this.firebaseService = firebaseService;
    }

    @PostConstruct
    private void warmup() {
        for(Ticket key: gitHubService.readAllIssues()) {
            firebaseService.writeGitHubDataToDB(key);
        }
    }
}
