package org.luvx.boot.rpc.dubbo.consumer;

import org.apache.dubbo.config.annotation.DubboReference;
import org.luvx.boot.rpc.dubbo.service.UserService;
import org.luvx.boot.rpc.dubbo.service.proto.user.UserRequest;
import org.luvx.boot.rpc.dubbo.service.proto.user.UserResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserServiceConsumer {
    @DubboReference(version = "1.0.0", check = false)
    private UserService userService;

    @DubboReference(version = "1.0.0", check = false)
    private org.luvx.boot.rpc.dubbo.service.proto.user.UserService protoUserService;

    @GetMapping("/index")
    public List<String> listNames() {
        return userService.listNames(System.currentTimeMillis());
    }

    @GetMapping("/index1")
    public String index1() {
        UserRequest r = UserRequest.newBuilder().setName("proto生成").build();
        UserResponse userResponse = protoUserService.oneToOne(r);
        return userResponse.getMessage();
    }
}
