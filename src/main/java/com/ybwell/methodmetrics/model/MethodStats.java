package com.ybwell.methodmetrics.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MethodStats {
    private String methodName;
    private long avgCostMicros; // 平均耗时（微秒）
    private long maxCostMicros; // 最大耗时
    private int calls;          // 调用次数
}