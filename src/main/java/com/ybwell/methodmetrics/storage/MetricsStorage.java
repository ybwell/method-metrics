package com.ybwell.methodmetrics.storage;

import com.ybwell.methodmetrics.model.MethodStats;
import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MetricsStorage {
    // key: 方法名, value: 最近1000次调用的耗时（微秒）
    private static final Map<String, CircularFifoQueue<Long>> METHOD_METRICS = new ConcurrentHashMap<>();

    public static void record(String methodName, long costMicros) {
        METHOD_METRICS.computeIfAbsent(methodName, k -> new CircularFifoQueue<>(1000))
                .add(costMicros);
    }

    public static MethodStats getStats(String methodName) {
        CircularFifoQueue<Long> queue = METHOD_METRICS.get(methodName);
        if (queue == null || queue.isEmpty()) return null;

        long total = 0, max = Long.MIN_VALUE;
        for (long cost : queue) {
            total += cost;
            max = Math.max(max, cost);
        }
        return new MethodStats(
                methodName,
                total / queue.size(), // 平均耗时
                max,                  // 最大耗时
                queue.size()          // 调用次数
        );
    }

    // 添加获取所有方法名的工具方法
    public static List<String> getAllMethods() {
        return new ArrayList<>(METHOD_METRICS.keySet());
    }
}