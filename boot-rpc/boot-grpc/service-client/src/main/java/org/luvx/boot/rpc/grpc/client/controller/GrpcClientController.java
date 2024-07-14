package org.luvx.boot.rpc.grpc.client.controller;

import org.luvx.boot.rpc.grpc.client.service.GrpcClientService;
import org.luvx.boot.rpc.grpc.service.proto.user.UserResponse;
import org.luvx.boot.web.response.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.List;

@RestController
public class GrpcClientController {
    @Resource
    private GrpcClientService grpcClientService;

    @RequestMapping("/oneToOne")
    public R<Object> printMessage(@RequestParam(defaultValue = "name") String name) {
        UserResponse userResponse = grpcClientService.oneToOne(name);
        return R.success(userResponse.getMessage());
    }

    @RequestMapping("/oneToMany")
    public R<Object> oneToMany(@RequestParam(defaultValue = "name") String name) {
        List<UserResponse> list = grpcClientService.oneToMany(name);
        List<String> list1 = list.stream().map(UserResponse::getMessage).toList();
        return R.success(list1);
    }


    @RequestMapping("/manyToOne")
    public R<Object> manyToOne() {
        grpcClientService.manyToOne();
        return R.success("ok");
    }

    @RequestMapping("/manyToMany")
    public R<Object> manyToMany() {
        grpcClientService.manyToMany();
        return R.success("ok");
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