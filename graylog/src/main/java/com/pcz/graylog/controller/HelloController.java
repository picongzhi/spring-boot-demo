package com.pcz.graylog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author picongzhi
 */
@RestController
@Slf4j
@RequestMapping("hello")
public class HelloController {
    @GetMapping
    public String hello() {
        log.info("hello graylog");
        return "hello graylog";
    }
}
