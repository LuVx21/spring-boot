package org.luvx.boot.resilience4j;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.luvx.coding.common.exception.BizException;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeoutException;

public class Demo {
    static final String breakerName = "myService";

    public static void main(String[] args) {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .minimumNumberOfCalls(10)
                .slowCallRateThreshold(50)
                .slowCallDurationThreshold(Duration.ofMillis(200))

                .waitDurationInOpenState(Duration.ofMillis(1000))
                .permittedNumberOfCallsInHalfOpenState(2)
                .slidingWindowSize(2)
                .recordExceptions(IOException.class, TimeoutException.class)
                .ignoreExceptions(BizException.class)

                .build();

        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(circuitBreakerConfig);
        CircuitBreaker circuitBreaker = registry.circuitBreaker(breakerName);
    }
}
