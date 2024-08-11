package org.luvx.boot.sentinel.config;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.luvx.boot.sentinel.controller.ResultWrapper;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Order(0)
@ControllerAdvice
public class SentinelSpringMvcBlockHandlerConfig {
    @ExceptionHandler(BlockException.class)
    public ResultWrapper sentinelBlockHandler(BlockException e) {
        log.warn("Blocked by Sentinel: {}", e.getRule());
        return new ResultWrapper(-1, "Blocked by Sentinel");
    }
}