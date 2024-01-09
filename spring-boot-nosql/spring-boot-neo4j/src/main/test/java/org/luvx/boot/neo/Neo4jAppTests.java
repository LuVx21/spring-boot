package org.luvx.boot.neo;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.luvx.boot.neo.repository.DeptRepository;
import org.luvx.boot.neo.repository.MovieRepository;
import org.luvx.boot.neo.repository.PersonRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class Neo4jAppTests {
    @Resource
    private PersonRepository personRepository;
    @Resource
    private MovieRepository  movieRepository;
    @Resource
    private DeptRepository   deptRepository;

    @Test
    void inti() {
    }
}
