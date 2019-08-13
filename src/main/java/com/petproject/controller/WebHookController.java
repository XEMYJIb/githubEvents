package com.petproject.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author anton.demus
 * @since 2019-08-06
 */
@RestController
@RequestMapping("/web_hook")
public class WebHookController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebHookController.class);

    @PostMapping("/github")
    public void processGithubWebHook(@RequestBody() Object some) {
        LOGGER.info("!!! {}", some);
    }

    @PostMapping("/freshdesk")
    public void processFreshdeskWebHook(@RequestBody() Object some) {

    }
}
