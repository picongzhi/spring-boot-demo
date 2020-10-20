package com.pcz.docker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author picongzhi
 */
@RestController
@RequestMapping
public class HelloController {
    @GetMapping
    public String hello() {
        return "Hello, From Docker!";
    }
}
