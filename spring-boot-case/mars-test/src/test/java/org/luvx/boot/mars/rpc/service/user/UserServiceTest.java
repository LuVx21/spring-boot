package org.luvx.boot.mars.rpc.service.user;

import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.luvx.boot.mars.BaseAppTests;
import org.luvx.boot.mars.rpc.proto.common.Int64;
import org.luvx.boot.mars.rpc.proto.user.UserResponse;
import org.luvx.boot.mars.rpc.proto.user.UserRpcService;
import org.luvx.boot.mars.rpc.sdk.count.CountRpcClient;
import org.luvx.boot.mars.rpc.sdk.user.UserCountType;

public class UserServiceTest extends BaseAppTests {
    @DubboReference(version = "1.0.0", check = false)
    private CountRpcClient countRpcClient;
    @DubboReference(version = "1.0.0", check = false)
    private UserRpcService userService;

    @Test
    void m0() {
        long i = countRpcClient.getByType(UserCountType.FANS_COUNT, 10000L);
        System.out.println(i);

        Int64 id = Int64.newBuilder().setData(10000L).build();
        UserResponse u = userService.getById(id);
        System.out.println(u);
    }
}
