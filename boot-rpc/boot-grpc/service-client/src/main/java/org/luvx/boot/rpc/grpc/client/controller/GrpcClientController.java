package org.luvx.boot.rpc.grpc.client.controller;

import org.luvx.boot.rpc.grpc.client.service.GrpcClientService;
import org.luvx.boot.rpc.grpc.service.proto.user.UserResponse;
import org.luvx.boot.web.response.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GrpcClientController {
    @Autowired
    private GrpcClientService grpcClientService;

    @RequestMapping("/test")
    public R<Object> printMessage(@RequestParam(defaultValue = "name") String name) {
        UserResponse userResponse = grpcClientService.selectUserInfo(name);
        return R.success(userResponse.getMessage());
    }
}