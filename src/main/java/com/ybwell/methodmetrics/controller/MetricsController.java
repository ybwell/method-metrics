package com.ybwell.methodmetrics.controller;

import com.ybwell.methodmetrics.model.MethodStats;
import com.ybwell.methodmetrics.storage.MetricsStorage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/metrics")
public class MetricsController {
    @GetMapping
    public List<MethodStats> getAllMetrics() {
        return MetricsStorage.getAllMethods().stream()
                .map(MetricsStorage::getStats)
                .collect(Collectors.toList());
    }

    @GetMapping("/method")
    public MethodStats getMethodMetrics(@RequestParam String name) {
        return MetricsStorage.getStats(name);
    }
}