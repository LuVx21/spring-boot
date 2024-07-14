package org.luvx.boot.mars.grpc.service.user;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.luvx.boot.mars.grpc.proto.common.Int64;
import org.luvx.boot.mars.grpc.proto.user.UserInfoGrpc.UserInfoImplBase;
import org.luvx.boot.mars.grpc.proto.user.UserResponse;
import org.luvx.boot.tools.dao.mapper.UserMapper;

import jakarta.annotation.Resource;

@Slf4j
@GrpcService
public class UserInfoGrpcService extends UserInfoImplBase {

    @Resource
    private UserMapper userMapper;

    @Override
    public void run(Empty request, StreamObserver<Empty> responseObserver) {
        log.info("空跑...");
        Empty empty = Empty.newBuilder().build();
        responseObserver.onNext(empty);
        responseObserver.onCompleted();
    }

    @Override
    public void getById(Int64 request, StreamObserver<UserResponse> responseObserver) {
        long data = request.getData();
        userMapper.selectByPrimaryKey(data);

        UserResponse r = UserResponse.newBuilder().build();
        responseObserver.onNext(r);
        responseObserver.onCompleted();
    }
}
