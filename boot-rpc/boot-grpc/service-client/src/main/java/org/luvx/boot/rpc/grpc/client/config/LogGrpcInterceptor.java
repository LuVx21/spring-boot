package org.luvx.boot.rpc.grpc.client.config;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ForwardingClientCall;
import io.grpc.ForwardingClientCallListener;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.Status;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogGrpcInterceptor implements ClientInterceptor {
    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
            MethodDescriptor<ReqT, RespT> method,
            CallOptions callOptions,
            Channel next
    ) {
        log.info("客户端请求:{}", method.getFullMethodName());
        return new ForwardingClientCall.SimpleForwardingClientCall<>(next.newCall(method, callOptions)) {
            @Override
            public void sendMessage(ReqT message) {
                log.info("请求参数->{}", message);
                super.sendMessage(message);
            }

            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                super.start(new ForwardingClientCallListener.SimpleForwardingClientCallListener<>(responseListener) {
                    @Override
                    public void onMessage(RespT message) {
                        log.info("响应内容->{}", message);
                        super.onMessage(message);
                    }

                    @Override
                    public void onHeaders(Metadata headers) {
                        log.info("请求头:{}", headers);
                        super.onHeaders(headers);
                    }

                    @Override
                    public void onClose(Status status, Metadata trailers) {
                        log.info("交互状态:{},元数据:{}", status, trailers);
                        super.onClose(status, trailers);
                    }
                }, headers);
            }
        };
    }

}