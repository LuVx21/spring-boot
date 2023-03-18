package org.luvx.boot.rpc.dubbo.service;

import java.util.List;

public interface UserService {
    List<String> listNames(Long id);
}