package org.luvx.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.luvx.service.AppService;

public class AppServiceImpl implements AppService {

    public List<String> listNames(Long id) {
        List<String> List = new ArrayList<>();
        List.add(String.format("LuVx_%d", id - 1));
        List.add(String.format("LuVx_%d", id));
        List.add(String.format("LuVx_%d", id + 1));
        return List;
    }
}
