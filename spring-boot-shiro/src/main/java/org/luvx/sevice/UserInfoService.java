package org.luvx.sevice;


import org.luvx.entity.UserInfo;

public interface UserInfoService {

    UserInfo findByUsername(String username);
}