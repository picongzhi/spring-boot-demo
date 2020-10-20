package com.pcz.upload.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author picongzhi
 */
@Controller
public class IndexController {
    @GetMapping("")
    public String index() {
        return "index";
    }
}
