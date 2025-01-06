package org.luvx.boot.rpc.grpc.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GrpcServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(GrpcServiceApp.class, args);
    }
}