package com.pcz.log.aop.controller;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author picongzhi
 */
@RestController
public class TestController {
    /**
     * 测试方法
     *
     * @param who 测试参数
     * @return Dict
     */
    @GetMapping("/test")
    public Dict test(@RequestParam(required = false, name = "who")
                             String who) {
        return Dict.create().set("who", StrUtil.isBlank(who) ? "me" : who);
    }
}
