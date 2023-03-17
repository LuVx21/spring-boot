package org.luvx.boot.rpc.grpc.service.config;

import io.grpc.ForwardingServerCall.SimpleForwardingServerCall;
import io.grpc.MethodDescriptor;
import io.grpc.ServerCall;
import lombok.extern.slf4j.Slf4j;

/**
 * 响应拦截器
 */
@Slf4j
public class GlobalCall<ReqT, RespT> extends SimpleForwardingServerCall<ReqT, RespT> {
    protected GlobalCall(ServerCall<ReqT, RespT> delegate) {
        super(delegate);
    }

    @Override
    protected ServerCall<ReqT, RespT> delegate() {
        return super.delegate();
    }

    @Override
    public MethodDescriptor<ReqT, RespT> getMethodDescriptor() {
        return super.getMethodDescriptor();
    }

    @Override
    public void sendMessage(RespT message) {
        log.info("响应内容:{}", message);
        super.sendMessage(message);
    }
}