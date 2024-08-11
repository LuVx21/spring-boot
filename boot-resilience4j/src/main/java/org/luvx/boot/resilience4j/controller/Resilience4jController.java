package org.luvx.boot.resilience4j.controller;

public class Resilience4jController {
    public static void main(String[] args) {
        // Supplier<String> supplier = () -> service.sayHelloWorld(param1);
        //
        // String result = Decorators.ofSupplier(supplier)
        //         .withBulkhead(Bulkhead.ofDefaults("name"))
        //         .withCircuitBreaker(CircuitBreaker.ofDefaults("name"))
        //         .withRetry(Retry.ofDefaults("name"))
        //         .withFallback(asList(CallNotPermittedException.class, BulkheadFullException.class),
        //                 throwable -> "Hello from fallback")
        //         .get();
    }
}
