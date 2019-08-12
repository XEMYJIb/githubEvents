package com.petproject.service;

import com.petproject.configuration.PropertiesProvider;
import com.petproject.exception.ApplicationRuntimeException;
import com.petproject.model.github.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author anton.demus
 * @since 2019-08-05
 */
@Service
public class GitHubService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GitHubService.class);

    private static final String LABEL_FEATURE = "feature";
    private static final String LABEL_BUG = "bug";

    private final RestService restService;
    private final PropertiesProvider propertiesProvider;
    private final Pattern pattern;

    @Inject
    public GitHubService(RestService restService,
                         PropertiesProvider propertiesProvider) {
        this.restService = restService;
        this.propertiesProvider = propertiesProvider;
        if (StringUtils.isNotBlank(propertiesProvider.getGithubDataParsingPattern())) {
            pattern = Pattern.compile(propertiesProvider.getGithubDataParsingPattern());
        } else {
            pattern = null;
        }
    }

    @PostConstruct
    private void validateWebhook() {
        boolean hookExists = false;
        Optional<Hook[]> nullableHooks =
            restService.getFromGitHub(propertiesProvider.getGithubRepoUrl() + "/hooks?state=active", Hook[].class);
        if (nullableHooks.isPresent()) {
            Hook[] hooks = nullableHooks.get();
            for (Hook hook : hooks) {
                if (hook.isActive() &&
                    "web".equals(hook.getName()) &&
                    hook.getUrl().endsWith("/web_hook/github")) {
                    for (String event : hook.getEvents()) {
                        if ("issue_comment".equals(event)) {
                            hookExists = true;
                        }
                    }
                }
            }
        }

        if (!hookExists) {
            String msg = "GitHub webhook does not exists";
            LOGGER.error(msg);
            throw new ApplicationRuntimeException(msg);
        }
    }

    public List<Ticket> readAllIssues() {
        final List<Ticket> result = new ArrayList<>();
        Optional<Issue[]> nullableIssues =
            restService.getFromGitHub(propertiesProvider.getGithubRepoUrl() + "/issues?state=open", Issue[].class);
        if (nullableIssues.isPresent()) {
            Issue[] issues = nullableIssues.get();
            for (Issue issue : issues) {
                if (issue.getComments() > 0) {
                    Optional<Comment[]> nullableComments =
                        restService.getFromGitHub(issue.getCommentsUrl(), Comment[].class);
                    if (nullableComments.isPresent()) {
                        Comment[] comments = nullableComments.get();
                        for (Comment comment : comments) {
                            final List<Integer> tickets = extractFreshdeskTicket(comment.getBody());
                            if (!tickets.isEmpty()) {
                                tickets.stream()
                                    .map(ticket -> Ticket.builder()
                                        .ghIssueNumber(issue.getNumber())
                                        .ghCommentId(comment.getId())
                                        .fdTicketNumber(ticket)
                                        .title(issue.getTitle())
                                        .label(extractLabel(issue.getLabels()))
                                        .issueCreated(issue.getCreated())
                                        .updatedDate(comment.getUpdatedAt())
                                        .issueLink(issue.getIssueUrl())
                                        .build()
                                    )
                                    .collect(Collectors.toCollection(() -> result));
                            }
                        }
                    }
                }
            }
        }

        return result;
    }

    private List<Integer> extractFreshdeskTicket(String commentBody) {
        final List<Integer> result = new ArrayList<>();

        if (StringUtils.isNotBlank(commentBody)) {
            if (pattern != null) {
                final Matcher matcher = pattern.matcher(commentBody);
                while (matcher.find()) {
                    String number = matcher.group(1);
                    result.add(Integer.parseInt(number));
                }
            }
        }

        return result;
    }

    private String extractLabel(List<Label> labels) {
        String label = "";
        Optional<String> opt = labels.stream()
            .map(Label::getName)
            .filter(lb -> LABEL_FEATURE.equalsIgnoreCase(lb) || LABEL_BUG.equalsIgnoreCase(lb))
            .findFirst();
        if (opt.isPresent()) {
            label = opt.get();
        }

        return label;
    }
}
