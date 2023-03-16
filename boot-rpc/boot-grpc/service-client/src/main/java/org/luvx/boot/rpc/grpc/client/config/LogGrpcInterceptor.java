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
            Channel next) {

        log.info("Received call to {}", method.getFullMethodName());
        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(next.newCall(method, callOptions)) {

            @Override
            public void sendMessage(ReqT message) {
                log.debug("Request message: {}", message);
                super.sendMessage(message);
            }

            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                super.start(
                        new ForwardingClientCallListener.SimpleForwardingClientCallListener<RespT>(responseListener) {
                            @Override
                            public void onMessage(RespT message) {
                                log.debug("Response message: {}", message);
                                super.onMessage(message);
                            }

                            @Override
                            public void onHeaders(Metadata headers) {
                                log.debug("gRPC headers: {}", headers);
                                super.onHeaders(headers);
                            }

                            @Override
                            public void onClose(Status status, Metadata trailers) {
                                log.info("Interaction ends with status: {}", status);
                                log.info("Trailers: {}", trailers);
                                super.onClose(status, trailers);
                            }
                        }, headers);
            }
        };
    }

}