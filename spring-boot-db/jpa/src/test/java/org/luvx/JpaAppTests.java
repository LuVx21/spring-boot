package org.luvx;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.luvx.repository.ArticleRepository;
import org.luvx.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jakarta.annotation.Resource;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class JpaAppTests {
    @Resource
    protected UserRepository    userRepository;
    @Resource
    protected ArticleRepository articleRepository;
}
