package org.luvx.boot.rpc.grpc.client;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.luvx.boot.rpc.grpc.client.service.GrpcClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {GrpcClientApp.class})
class GrpcClientAppTest {
    @Autowired
    private GrpcClientService grpcClientService;

    @Test
    void m1() {
        grpcClientService.oneToOne("name");
    }
}