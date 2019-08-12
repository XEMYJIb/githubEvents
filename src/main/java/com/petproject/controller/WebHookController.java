package com.petproject.controller;

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

    @PostMapping("/github")
    public void processGithubWebHook(@RequestBody() Object some) {
        
    }

    @PostMapping("/freshdesk")
    public void processFreshdeskWebHook(@RequestBody() Object some) {

    }
}
