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
    public void selectUserInfo(UserRequest request, StreamObserver<UserResponse> responseObserver) {
        UserResponse response = UserResponse.newBuilder()
                .setMessage("selectUserInfo: " + request.getName())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * one - many
     */
    @Override
    public void selectUserInfo1(UserRequest request, StreamObserver<UserResponse> responseObserver) {
        Builder builder = UserResponse.newBuilder();
        for (int i = 0; i < 3; i++) {
            UserResponse response = builder
                    .setMessage("selectUserInfo1: " + request.getName() + i)
                    .build();
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<UserRequest> selectUserInfo2(StreamObserver<UserResponseList> responseObserver) {
        return new StreamObserver<UserRequest>() {
            private int cnt;

            @Override
            public void onNext(UserRequest request) {
                log.info("请求No:{} {}", ++cnt, request.getName());
                UserResponseList.Builder builder = UserResponseList.newBuilder();
                for (int i = 0; i < 3; i++) {
                    builder.addUsers(
                            UserResponse.newBuilder()
                                    .setMessage("返回=" + request.getName() + "-" + i + "请求参数cnt:" + cnt)
                                    .build());
                }
                responseObserver.onNext(builder.build());
                responseObserver.onCompleted();
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("", throwable);
            }

            @Override
            public void onCompleted() {
            }
        };
    }

    @Override
    public StreamObserver<UserRequest> selectUserInfo3(StreamObserver<UserResponse> responseObserver) {
        return new StreamObserver<UserRequest>() {
            private int cnt;

            @Override
            public void onNext(UserRequest request) {
                log.info("请求No:{} {}", ++cnt, request.getName());
                responseObserver.onNext(UserResponse.newBuilder().setMessage("请求参数cnt:" + cnt).build());
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
