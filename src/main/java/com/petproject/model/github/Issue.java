package com.petproject.model.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;

import java.util.Date;
import java.util.List;

/**
 * @author anton.demus
 * @since 2019-08-06
 */
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Issue {
    private String title;
    private int number;
    private int comments;
    private List<Label> labels;
    private String issueUrl;
    private String commentsUrl;
    private Date created;

    @JsonSetter("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonSetter("number")
    public void setNumber(int number) {
        this.number = number;
    }

    @JsonSetter("comments")
    public void setComments(int comments) {
        this.comments = comments;
    }

    @JsonSetter("labels")
    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    @JsonSetter("comments_url")
    public void setCommentsUrl(String commentsUrl) {
        this.commentsUrl = commentsUrl;
    }

    @JsonSetter("created_at")
    public void setCreated(Date created) {
        this.created = created;
    }

    @JsonSetter("html_url")
    public void setIssueUrl(String issueUrl) {
        this.issueUrl = issueUrl;
    }
}
