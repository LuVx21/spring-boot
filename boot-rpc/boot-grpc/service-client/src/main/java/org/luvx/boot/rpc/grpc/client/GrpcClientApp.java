package org.luvx.boot.rpc.grpc.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GrpcClientApp {
    public static void main(String[] args) {
        SpringApplication.run(GrpcClientApp.class, args);
    }
}