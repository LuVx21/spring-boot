package org.luvx.boot.es.service.impl;

import org.luvx.boot.es.entity.User;
import org.luvx.boot.es.service.AbstractEsService;
import org.springframework.stereotype.Service;

@Service
public class UserEsService extends AbstractEsService<User> {
    @Override
    protected String indexName() {
        return User.INDEX;
    }
}
