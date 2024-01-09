package org.luvx.boot.mongo.service;

import org.luvx.boot.mongo.base.AbstractMongoService;
import org.luvx.boot.mongo.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractMongoService<User> {
}
