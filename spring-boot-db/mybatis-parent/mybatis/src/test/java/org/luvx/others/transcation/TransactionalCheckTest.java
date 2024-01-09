package org.luvx.others.transcation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.luvx.uncrud.transcation.TransactionalCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TransactionalCheckTest {
    @Autowired
    TransactionalCheck service;

    @Test
    public void testMethod() {
        service.method0();
    }
}