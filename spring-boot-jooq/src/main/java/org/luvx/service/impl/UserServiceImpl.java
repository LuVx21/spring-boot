package org.luvx.service.impl;

import org.jooq.generated.tables.User;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.luvx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

/**
 * @ClassName: org.luvx.service.impl
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/11/6 9:59
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    DSLContext dsl;

    org.jooq.generated.tables.User u = User.USER.as("user");

    @Override
    public void delete(int id) {
        dsl.delete(u).where(u.USER_ID.eq((long) id));
    }

    @Override
    public void insert(org.luvx.pojos.User user) {
        dsl.insertInto(u).
                columns(u.AGE, u.PASSWORD, u.USER_NAME).
                values(user.getAge(), user.getPassword(), user.getUserName())
                .execute();
    }

    @Override
    public int update(org.luvx.pojos.User user) {
        dsl.update(u).set((Record) user);
        return 0;
    }

    @Override
    public org.luvx.pojos.User selectById(int id) {
        Result result = dsl.select(u.USER_ID, u.AGE, u.PASSWORD, u.USER_NAME)
                .from(u)
                .where(u.USER_ID.eq((long) id)).fetch();
        System.out.println(result.get(0).toString());
        String className = result.get(0).getClass().getName();
        System.out.println(className);

        Record record = (Record) result.get(0);
        System.out.println(record);

        return null;
    }

    @Override
    public Iterator<org.luvx.pojos.User> selectAll(int pageNum, int pageSize) {
        Result result = dsl.select().from(u).fetch();
        return result.iterator();
    }
}
