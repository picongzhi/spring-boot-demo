package com.pcz.dubbo.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.pcz.dubbo.common.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author picongzhi
 */
@Service
@Component
@Slf4j
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        log.info("someone is calling me...");
        return "say hello to: " + name;
    }
}
