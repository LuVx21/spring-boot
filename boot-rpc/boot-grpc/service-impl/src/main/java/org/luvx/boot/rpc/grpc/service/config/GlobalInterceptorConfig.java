package org.luvx.boot.rpc.grpc.service.config;

import java.util.stream.Collectors;

import io.grpc.Metadata;
import io.grpc.Metadata.Key;
import io.grpc.MethodDescriptor;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.springframework.context.annotation.Configuration;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

@Configuration(proxyBeanMethods = false)
public class GlobalInterceptorConfig {
    @GrpcGlobalServerInterceptor
    LogGrpcInterceptor logServerInterceptor() {
        return new LogGrpcInterceptor();
    }

    @Slf4j
    public static class LogGrpcInterceptor implements ServerInterceptor {
        @Override
        public <ReqT, RespT> Listener<ReqT> interceptCall(
                ServerCall<ReqT, RespT> call, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler
        ) {
            MethodDescriptor<ReqT, RespT> descriptor = call.getMethodDescriptor();
            log.info("请求服务:{},请求方法:{}", descriptor.getServiceName(), descriptor.getBareMethodName());
            String headers = metadata.keys().stream()
                    .map(key -> key + ": " + metadata.get(Key.of(key, ASCII_STRING_MARSHALLER)))
                    .collect(Collectors.joining("\n"));
            log.info("请求头:\n{}", headers);
            return new GlobalCallListener<>(serverCallHandler.startCall(new GlobalCall<>(call), metadata));
        }
    }
}
