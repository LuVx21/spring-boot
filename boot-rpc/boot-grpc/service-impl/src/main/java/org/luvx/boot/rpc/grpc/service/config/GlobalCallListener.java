package org.luvx.boot.rpc.grpc.service.config;

import io.grpc.ForwardingServerCallListener;
import io.grpc.ServerCall.Listener;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求拦截器
 */
@Slf4j
public class GlobalCallListener<R> extends ForwardingServerCallListener<R> {
    private final Listener<R> delegate;

    public GlobalCallListener(Listener<R> delegate) {
        this.delegate = delegate;
    }

    @Override
    protected Listener<R> delegate() {
        return delegate;
    }

    @Override
    public void onMessage(R message) {
        log.info("请求参数:{}", message);
        super.onMessage(message);
    }
}