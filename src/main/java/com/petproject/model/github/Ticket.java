package com.petproject.model.github;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

/**
 * @author anton.demus
 * @since 2019-08-06
 */
@Getter
@Builder
public class Ticket {
    private final int ghIssueNumber;
    private final String issueLink;
    private final long ghCommentId;
    private final int fdTicketNumber;
    private final String title;
    private final String label;
    private final Date issueCreated;
    private final Date updatedDate;
}
