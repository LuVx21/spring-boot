package org.luvx.boot.resilience4j;

import org.apache.commons.lang3.concurrent.CircuitBreaker;

import java.time.Duration;

public class Demo {
    // public static void main(String[] args) {
    //     CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
    //             .failureRateThreshold(50)
    //             .minimumNumberOfCalls(10)
    //             .slowCallRateThreshold(50)
    //             .slowCallDurationThreshold(Duration.ofMillis(200))
    //             .build();
    //
    //
    //     CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(circuitBreakerConfig);
    //     CircuitBreaker circuitBreaker = registry.circuitBreaker("myService");
    //
    //
    // }
    //
    //
    // public String callMyService(String input) {
    //     if (circuitBreaker.isOpen()) {
    //         return "Service unavailable due to circuit breaker open";
    //     }
    //     try {
    //         // 调用服务
    //         return myService.doSomething(input);
    //     } catch (Exception e) {
    //         // 记录异常信息
    //         circuitBreaker.recordFailure(e);
    //         return "Service call failed";
    //     }
    // }
}
