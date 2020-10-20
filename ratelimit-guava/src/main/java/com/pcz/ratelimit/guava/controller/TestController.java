package com.pcz.ratelimit.guava.controller;

import cn.hutool.core.lang.Dict;
import com.pcz.ratelimit.guava.annotation.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author picongzhi
 */
@Slf4j
@RestController
public class TestController {
    @GetMapping("/test1")
    @RateLimiter(value = 1.0, timeout = 300)
    public Dict test1() {
        log.info("[test1]被执行...");
        return Dict.create().set("msg", "hello, world").set("description", "别想一直看到我，不信你快速刷新看看~");
    }

    @GetMapping("/test2")
    public Dict test2() {
        log.info("[test2]被执行...");
        return Dict.create().set("msg", "hello, world").set("description", "我一直都在，卟离卟弃");
    }

    @GetMapping("/test3")
    @RateLimiter(value = 2.0, timeout = 300)
    public Dict test3() {
        log.info("[test3]被执行...");
        return Dict.create().set("msg", "hello, world").set("description", "别想一直看到我，不信你快速刷新看看~");
    }
}
