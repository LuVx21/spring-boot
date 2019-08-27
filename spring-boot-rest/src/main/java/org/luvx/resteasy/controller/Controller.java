package org.luvx.resteasy.controller;

import com.alibaba.fastjson.JSON;
import org.luvx.resteasy.model.User;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;

/**
 * @ClassName: org.luvx.resteasy
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/8/27 16:18
 */
@Component
@Path("/user")
@Consumes(MediaType.APPLICATION_JSON_UTF8_VALUE)
@Produces(MediaType.APPLICATION_JSON_UTF8_VALUE)
public class Controller {
    /**
     * http://127.0.0.1:8090/api/user/v2/xieren
     *
     * @param name
     * @return
     */
    @GET
    @Path("/v2/{name}")
    public String getUser(@PathParam("name") String name) {
        User user = new User();
        user.setId(19L);
        user.setName(name);
        return JSON.toJSONString(user);
    }

    /**
     * http://127.0.0.1:8090/api/user/v3
     *
     * @param user
     * @return
     */
    @GET
    @Path("/v3")
    public String getUser(User user) {
        System.out.println(user);
        return JSON.toJSONString(user);
    }
}
