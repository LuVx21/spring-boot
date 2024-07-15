package org.luvx.boot.rpc.dubbo.service.impl.impl;

import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.annotation.DubboService;
import org.luvx.boot.rpc.dubbo.service.proto.user.DubboUserServiceTriple;
import org.luvx.boot.rpc.dubbo.service.proto.user.UserRequest;
import org.luvx.boot.rpc.dubbo.service.proto.user.UserResponse;

/**
 * IDL 方式调用服务
 */
@DubboService(version = "1.0.0")
public class UserServiceImpl1 extends DubboUserServiceTriple.UserServiceImplBase {
    @Override
    public UserResponse oneToOne(UserRequest request) {
        return UserResponse.newBuilder()
                .setMessage("one2one(直接返回): " + request.getName())
                .build();
    }

    @Override
    public void oneToMany(UserRequest request, StreamObserver<UserResponse> responseObserver) {
        UserResponse.Builder builder = UserResponse.newBuilder();
        for (int i = 0; i < 3; i++) {
            UserResponse response = builder
                    .setMessage("one2many: " + request.getName() + "_" + i)
                    .build();
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }
}