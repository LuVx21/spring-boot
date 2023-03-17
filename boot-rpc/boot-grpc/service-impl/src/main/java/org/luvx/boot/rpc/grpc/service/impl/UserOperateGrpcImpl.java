package org.luvx.boot.rpc.grpc.service.impl;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.luvx.boot.rpc.grpc.service.proto.user.UserOperateGrpc.UserOperateImplBase;
import org.luvx.boot.rpc.grpc.service.proto.user.UserRequest;
import org.luvx.boot.rpc.grpc.service.proto.user.UserResponse;
import org.springframework.security.access.annotation.Secured;

@Slf4j
@GrpcService
public class UserOperateGrpcImpl extends UserOperateImplBase {
    @Override
    @Secured("ROLE_GREET")
    public void updatePassword(UserRequest request, StreamObserver<UserResponse> responseObserver) {
        UserResponse response = UserResponse.newBuilder()
                .setMessage("更新密码..." + request.getId())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
