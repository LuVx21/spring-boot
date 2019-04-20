package org.luvx.api.java8.lambda.jdk;

import org.junit.Before;
import org.junit.Test;
import org.luvx.entity.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * jdk中Lambda使用范例
 */
public class Usage {

    private List<User> users = new ArrayList<>();

    @Before
    public void init() {
        users.add(new User(10L, "LuVx", "1", 20));
        users.add(new User(11L, "LuVx", "2", 20));
        users.add(new User(20L, "foo", "3", 20));
        users.add(new User(22L, "bar", "4", 20));
    }

    @Test
    public void method1() {
        users.forEach(user -> System.out.println(user.getUserName()));
    }

    @Test
    public void method3() {
        File dir = new File("/home/dir/");
        File[] dirs = dir.listFiles(f -> f.isDirectory());
    }
}
