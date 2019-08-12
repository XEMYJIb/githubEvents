package com.petproject.model.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * @author anton.demus
 * @since 2019-08-06
 */
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Hook {
    private long id;
    private String name;
    private boolean active;
    private List<String> events;
    private Config config;

    @JsonSetter("id")
    public void setId(long id) {
        this.id = id;
    }

    @JsonSetter("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonSetter("active")
    public void setActive(boolean active) {
        this.active = active;
    }

    @JsonSetter("events")
    public void setEvents(List<String> events) {
        this.events = events;
    }

    @JsonSetter("config")
    public void setConfig(Config config) {
        this.config = config;
    }

    public String getUrl() {
        return config.url;
    }

    public class Config {
        private String url;

        @JsonSetter("url")
        public void setUrl(String url) {
            this.url = url;
        }
    }
}
