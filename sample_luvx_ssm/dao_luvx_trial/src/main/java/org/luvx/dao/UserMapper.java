package org.luvx.dao;

import org.luvx.annotation.MeasurementAnno;
import org.luvx.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    void save(User user);

    @MeasurementAnno
    User findById(long id);

    List<User> findAll();

    void update(User user);

    void deleteById(long id);
}
