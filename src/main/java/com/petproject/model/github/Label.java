package com.petproject.model.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;

/**
 * @author anton.demus
 * @since 2019-08-06
 */
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Label {
    private String name;

    @JsonSetter("name")
    public void setName(String name) {
        this.name = name;
    }
}
