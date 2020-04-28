package org.luvx.module.repo;

import io.ebean.EbeanServer;
import org.luvx.module.domain.User;
import org.luvx.common.base.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends BaseRepository<Long, User> {
    @Autowired
    public UserRepository(EbeanServer server) {
        super(User.class, server);
    }
}
