package org.luvx.boot.neo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@ComponentScan({"org.luvx.boot"})
@EnableNeo4jRepositories
@SpringBootApplication
public class Neo4jApp {
    public static void main(String[] args) {
        SpringApplication.run(Neo4jApp.class, args);
    }
}
