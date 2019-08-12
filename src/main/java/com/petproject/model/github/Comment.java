package com.petproject.model.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;

import java.util.Date;

/**
 * @author anton.demus
 * @since 2019-08-05
 */
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment {
    private long id;
    private String body;
    private Date updatedAt;

    @JsonSetter("id")
    public void setId(long id) {
        this.id = id;
    }

    @JsonSetter("body")
    public void setBody(String body) {
        this.body = body;
    }

    @JsonSetter("updated_at")
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
