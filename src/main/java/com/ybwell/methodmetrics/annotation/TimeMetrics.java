package com.ybwell.methodmetrics.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD) // 只能用在方法上
@Retention(RetentionPolicy.RUNTIME) // 运行时保留
public @interface TimeMetrics {
    String value() default ""; // 可选：自定义指标名称
}