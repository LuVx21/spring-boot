package org.luvx.boot.rpc.grpc.service.config;

import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class GlobalInterceptorConfiguration {
    @GrpcGlobalServerInterceptor
    LogGrpcInterceptor logServerInterceptor() {
        return new LogGrpcInterceptor();
    }

    @Slf4j
    public static class LogGrpcInterceptor implements ServerInterceptor {
        @Override
        public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
                ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler
        ) {
            MethodDescriptor<ReqT, RespT> descriptor = serverCall.getMethodDescriptor();
            log.info("请求服务:{},请求方法:{}", descriptor.getServiceName(), descriptor.getBareMethodName());
            return serverCallHandler.startCall(serverCall, metadata);
        }
    }
}
