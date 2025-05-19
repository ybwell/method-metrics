package com.ybwell.methodmetrics.aspect;

import com.ybwell.methodmetrics.annotation.TimeMetrics;
import com.ybwell.methodmetrics.storage.MetricsStorage;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MethodTimeAspect {
    @Around("@annotation(timeMetrics)") // 拦截带@TimeMetrics注解的方法
    public Object around(ProceedingJoinPoint pjp, TimeMetrics timeMetrics) throws Throwable {
        long start = System.nanoTime();
        try {
            return pjp.proceed();
        } finally {
            long costMicros = (System.nanoTime() - start) / 1000;
            String methodName = getMethodName(pjp, timeMetrics);
            MetricsStorage.record(methodName, costMicros);
        }
    }

    private String getMethodName(ProceedingJoinPoint pjp, TimeMetrics annotation) {
        // 如果注解指定了名称，优先使用
        if (!annotation.value().isEmpty()) {
            return annotation.value();
        }
        // 默认生成：类名+方法名
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        return signature.getDeclaringType().getSimpleName() + "#" + signature.getName();
    }
}