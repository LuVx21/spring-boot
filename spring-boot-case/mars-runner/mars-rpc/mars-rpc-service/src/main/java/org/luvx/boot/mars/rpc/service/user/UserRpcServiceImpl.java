package org.luvx.boot.mars.rpc.service.user;

import com.google.protobuf.Empty;
import com.google.protobuf.Timestamp;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.luvx.boot.mars.rpc.proto.common.Int64;
import org.luvx.boot.mars.rpc.proto.user.DubboUserRpcServiceTriple;
import org.luvx.boot.mars.rpc.proto.user.UserResponse;
import org.luvx.boot.mars.dao.entity.User;
import org.luvx.boot.mars.dao.mapper.UserMapper;
import org.luvx.coding.common.util.DateUtils;

import jakarta.annotation.Resource;
import java.util.Optional;

@Slf4j
@DubboService(version = "1.0.0")
public class UserRpcServiceImpl extends DubboUserRpcServiceTriple.UserRpcServiceImplBase {
    @Resource
    private UserMapper userMapper;

    @Override
    public UserResponse getById(Int64 request) {
        Optional<User> user = userMapper.selectByPrimaryKey(request.getData());
        return user.map(this::convert2Proto).orElseGet(UserResponse::getDefaultInstance);
    }

    @Override
    public Empty run(Empty request) {
        log.info("空跑...");
        return Empty.newBuilder().build();
    }

    private UserResponse convert2Proto(User u) {
        long l = DateUtils.dateTimeToTimestamp(u.getBirthday());
        return UserResponse.newBuilder()
                .setId(u.getId())
                .setUserName(u.getUserName())
                .setPassword(u.getPassword())
                .setAge(u.getAge())
                .setBirthday(Timestamp.newBuilder().setSeconds(l).build())
                .build();
    }
}
