package org.luvx.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.luvx.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {

    void add(User user);

    void deleteById(Long id);

    void deleteByUserName(String userName);

    void update(User user);

    List<User> getAll();

    User getById(Long id);
}