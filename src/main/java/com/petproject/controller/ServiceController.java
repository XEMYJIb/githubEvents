package com.petproject.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author anton.demus
 * @since 2019-08-12
 */
@RestController
public class ServiceController {

    @GetMapping("/healthCheck")
    public HttpStatus healthCheck() {
        return HttpStatus.OK;
    }
}
