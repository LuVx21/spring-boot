package org.luvx.boot.rpc.grpc.service.impl;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.luvx.boot.rpc.grpc.service.proto.user.UserInfoGrpc.UserInfoImplBase;
import org.luvx.boot.rpc.grpc.service.proto.user.UserRequest;
import org.luvx.boot.rpc.grpc.service.proto.user.UserResponse;
import org.luvx.boot.rpc.grpc.service.proto.user.UserResponse.Builder;
import org.luvx.boot.rpc.grpc.service.proto.user.UserResponseList;

@Slf4j
@GrpcService
public class UserInfoGrpcImpl extends UserInfoImplBase {
    /**
     * one - one
     */
    @Override
    public void oneToOne(UserRequest request, StreamObserver<UserResponse> responseObserver) {
        UserResponse response = UserResponse.newBuilder()
                .setMessage("one2one: " + request.getName())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * one - many
     */
    @Override
    public void oneToMany(UserRequest request, StreamObserver<UserResponse> responseObserver) {
        Builder builder = UserResponse.newBuilder();
        for (int i = 0; i < 3; i++) {
            UserResponse response = builder
                    .setMessage("one2many: " + request.getName() + "_" + i)
                    .build();
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<UserRequest> manyToOne(StreamObserver<UserResponseList> responseObserver) {
        return new StreamObserver<>() {
            private int cnt;

            @Override
            public void onNext(UserRequest request) {
                log.info("manyToOne 请求No:{} {}", ++cnt, request.getName());
                UserResponseList.Builder builder = UserResponseList.newBuilder();
                for (int i = 0; i < 3; i++) {
                    builder.addUsers(
                            UserResponse.newBuilder()
                                    .setMessage("manyToOne 返回=" + request.getName() + "-" + i + "请求参数cnt:" + cnt)
                                    .build());
                }
                responseObserver.onNext(builder.build());
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("", throwable);
                responseObserver.onError(throwable);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<UserRequest> manyToMany(StreamObserver<UserResponse> responseObserver) {
        return new StreamObserver<>() {
            private int cnt;

            @Override
            public void onNext(UserRequest request) {
                log.info("manyToMany请求No:{} {}", ++cnt, request.getName());
                responseObserver.onNext(UserResponse.newBuilder().setMessage("manyToMany请求参数cnt:" + cnt).build());
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("", throwable);
            }

            @Override
            public void onCompleted() {
                for (int i = 0; i < 2; i++) {
                    responseObserver.onNext(UserResponse.newBuilder().setMessage("foo" + i).build());
                }
                responseObserver.onCompleted();
            }
        };
    }
}
