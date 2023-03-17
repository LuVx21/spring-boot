package org.luvx.boot.rpc.grpc.client.controller;

import javax.annotation.Resource;

import org.luvx.boot.rpc.grpc.client.service.GrpcClientService;
import org.luvx.boot.rpc.grpc.service.proto.user.UserResponse;
import org.luvx.boot.web.response.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GrpcClientController {
    @Resource
    private GrpcClientService grpcClientService;

    @RequestMapping("/selectUserInfo")
    public R<Object> printMessage(@RequestParam(defaultValue = "name") String name) {
        UserResponse userResponse = grpcClientService.selectUserInfo(name);
        return R.success(userResponse.getMessage());
    }

    @RequestMapping("/updatePassword")
    public R<Object> updatePassword(
            long id,
            @RequestParam(defaultValue = "password") String password
    ) {
        UserResponse userResponse = grpcClientService.updatePassword(id, password);
        return R.success(userResponse.getMessage());
    }
}