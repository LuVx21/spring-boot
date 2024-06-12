package org.luvx.boot.r2dbc;

import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.Test;
import org.luvx.boot.r2dbc.entity.User;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.CriteriaDefinition;
import org.springframework.data.relational.core.query.Query;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;

import jakarta.annotation.Resource;

import static org.springframework.data.domain.Sort.by;
import static org.springframework.data.relational.core.query.Query.query;
import static org.springframework.data.relational.domain.SqlSort.SqlOrder.desc;

class TemplateTest extends BaseAppTests {
    @Resource
    private R2dbcEntityTemplate template;
    @Resource
    private ConnectionFactory   connectionFactory;

    @Test
    void select() {
        CriteriaDefinition in = Criteria.where("user_name").is("foo")
                .and("age").in(18, 19);
        Query sort = query(in).sort(by(desc("id")));
        Flux<User> all = template.select(User.class)
                .matching(sort)
                .all();
        System.out.println(all.blockFirst());
    }

    @Test
    void select1() {
        DatabaseClient client = DatabaseClient.create(connectionFactory);
        Flux<User> all = client.sql("select * from user where id = 1")
                .mapProperties(User.class)
                .all();
        System.out.println(all.blockFirst());
    }
}