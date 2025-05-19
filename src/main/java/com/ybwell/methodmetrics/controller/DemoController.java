package com.ybwell.methodmetrics.controller;

import com.ybwell.methodmetrics.annotation.TimeMetrics;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/fast")
    @TimeMetrics("快速接口")
    public String fast() throws InterruptedException {
        Thread.sleep(50); // 模拟业务逻辑
        return "OK";
    }

    @GetMapping("/slow")
    @TimeMetrics("慢速接口")
    public String slow() throws InterruptedException {
        Thread.sleep(200);
        return "OK";
    }
}