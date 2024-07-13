package org.luvx.boot.rpc.grpc.client.service;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.luvx.boot.rpc.grpc.service.proto.user.UserInfoGrpc.UserInfoBlockingStub;
import org.luvx.boot.rpc.grpc.service.proto.user.UserOperateGrpc.UserOperateBlockingStub;
import org.luvx.boot.rpc.grpc.service.proto.user.UserRequest;
import org.luvx.boot.rpc.grpc.service.proto.user.UserResponse;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class GrpcClientService {
    @GrpcClient("service-impl")
    private UserInfoBlockingStub    userInfoStub;
    @GrpcClient("service-impl")
    private UserOperateBlockingStub userOperateBlockingStub;

    public UserResponse oneToOne(String name) {
        UserRequest request = UserRequest.newBuilder().setName(name).build();
        UserResponse userResponse = userInfoStub.oneToOne(request);
        log.info("入:{} 出:{}", name, userResponse);
        return userResponse;
    }

    public List<UserResponse> oneToMany(String name) {
        UserRequest request = UserRequest.newBuilder().setName(name).build();
        Iterator<UserResponse> it = userInfoStub.oneToMany(request);
        return Lists.newArrayList(it);
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
