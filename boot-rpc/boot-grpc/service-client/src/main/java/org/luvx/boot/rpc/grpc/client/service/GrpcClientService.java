package org.luvx.boot.rpc.grpc.client.service;

import com.google.common.collect.Lists;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.luvx.boot.rpc.grpc.service.proto.user.UserInfoGrpc.UserInfoBlockingStub;
import org.luvx.boot.rpc.grpc.service.proto.user.UserInfoGrpc.UserInfoFutureStub;
import org.luvx.boot.rpc.grpc.service.proto.user.UserInfoGrpc.UserInfoStub;
import org.luvx.boot.rpc.grpc.service.proto.user.UserOperateGrpc.UserOperateBlockingStub;
import org.luvx.boot.rpc.grpc.service.proto.user.UserRequest;
import org.luvx.boot.rpc.grpc.service.proto.user.UserResponse;
import org.luvx.boot.rpc.grpc.service.proto.user.UserResponseList;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class GrpcClientService {
    @GrpcClient("service-impl")
    private UserInfoStub         userInfoStub;
    @GrpcClient("service-impl")
    private UserInfoBlockingStub userInfoBlockingStub;
    @GrpcClient("service-impl")
    private UserInfoFutureStub   userInfoFutureStub;

    @GrpcClient("service-impl")
    private UserOperateBlockingStub userOperateBlockingStub;

    public UserResponse oneToOne(String name) {
        UserRequest request = UserRequest.newBuilder().setName(name).build();
        UserResponse userResponse = userInfoBlockingStub.oneToOne(request);
        log.info("入:{} 出:{}", name, userResponse);
        return userResponse;
    }

    public List<UserResponse> oneToMany(String name) {
        UserRequest request = UserRequest.newBuilder().setName(name).build();
        Iterator<UserResponse> it = userInfoBlockingStub.oneToMany(request);
        return Lists.newArrayList(it);
    }

    public void manyToOne() {
        UserRequest request = UserRequest.newBuilder()
                .setName("foobar")
                .build();
        StreamObserver<UserRequest> requestStreamObserver = userInfoStub.manyToOne(
                new StreamObserver<>() {
                    @Override
                    public void onNext(UserResponseList value) {
                        System.out.println("--------------onNext--------------");
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.out.println("--------------onError--------------");
                        // manyToOne();
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("--------------manyToOne--------------");
                        // manyToOne();
                    }
                });
        requestStreamObserver.onNext(request);
        requestStreamObserver.onCompleted();
    }

    public void manyToMany() {
        UserRequest request = UserRequest.newBuilder()
                .setName("foobar")
                .build();
        StreamObserver<UserRequest> requestStreamObserver = userInfoStub.manyToMany(
                new StreamObserver<>() {
                    @Override
                    public void onNext(UserResponse value) {
                    }

                    @Override
                    public void onError(Throwable t) {
                        manyToMany();
                    }

                    @Override
                    public void onCompleted() {
                        manyToMany();
                    }
                });
        requestStreamObserver.onNext(request);
        requestStreamObserver.onCompleted();
    }

    public UserResponse updatePassword(long id, String password) {
        UserRequest request = UserRequest.newBuilder()
                .setId(id)
                .setPassword(password)
                .build();
        UserResponse userResponse = userOperateBlockingStub.updatePassword(request);
        log.info("入:{} 出:{}", password, userResponse);
        return userResponse;
    }
}
