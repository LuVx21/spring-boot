package org.luvx.boot.es.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.luvx.boot.es.EsAppTests;

@Slf4j
public class DeleteTest extends EsAppTests {

    @Test
    void m1() {
        for (int i = 101; i <= 120; i++) {
            userEsService.deleteById(String.valueOf(i));
        }
    }
}