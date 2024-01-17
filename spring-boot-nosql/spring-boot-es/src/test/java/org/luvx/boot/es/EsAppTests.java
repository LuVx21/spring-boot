package org.luvx.boot.es;

import org.junit.jupiter.api.extension.ExtendWith;
import org.luvx.boot.es.repository.UserRepository;
import org.luvx.boot.es.service.impl.UserEsService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jakarta.annotation.Resource;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class EsAppTests {
    @Resource
    protected ElasticsearchTemplate template;
    @Resource
    protected UserRepository        userRepository;
    @Resource
    protected UserEsService         userEsService;
}
