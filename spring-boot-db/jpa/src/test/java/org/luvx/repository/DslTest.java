package org.luvx.repository;

import com.alibaba.fastjson2.JSON;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.luvx.JpaAppTests;
import org.luvx.entity.QArticle;
import org.luvx.entity.QUser;
import org.luvx.entity.User;
import org.luvx.entity.dto.UserArticleVo;

import jakarta.annotation.Resource;
import java.util.List;

@Slf4j
class DslTest extends JpaAppTests {
    @Resource
    JPAQueryFactory queryFactory;

    @Test
    void testUser() {
        QUser user = QUser.user;
        BooleanExpression eq = user.id.eq(1L);
        userRepository.findAll(eq).forEach(System.out::println);

        BooleanExpression in = user.id.in(1L, 10000L);
        userRepository.findAll(in).forEach(System.out::println);

        List<String> nameList = queryFactory.select(user.userName)
                .from(user)
                .where(eq, user.password.eq("bar"))
                .fetch();
        log.info("name:{}", nameList);
        List<User> memberList = queryFactory.selectFrom(user)
                .where(eq)
                .orderBy(user.id.asc())
                .offset(0L).limit(3L)
                .fetch();
        log.info("b:{}", JSON.toJSONString(memberList));
    }

    @Test
    void testJoin() {
        QUser user = QUser.user;
        QArticle article = QArticle.article;

        List<UserArticleVo> dtoList = queryFactory
                .select(
                        Projections.fields(UserArticleVo.class, user.id.as("userId"), article.id.as("articleId"), user.userName)
                )
                .from(user)
                // .leftJoin(article)
                .innerJoin(article)
                .on(user.id.eq(article.userId), user.id.eq(1L))
                .fetch();
        log.info("c:{}", JSON.toJSONString(dtoList));

        List<User> subList = queryFactory.selectFrom(user)
                .where(user.id.in(JPAExpressions.selectDistinct(article.userId).from(article)))
                .fetch();
    }

    @Test
        // @Transactional(rollbackFor = Exception.class)
    void update() {
        QUser user = QUser.user;
        queryFactory.update(user).set(user.age, 11)
                .where(user.id.eq(1L))
                .execute();

        // QUser user = QUser.user;
        // queryFactory.delete(user)
        //         .where(user.id.eq(1L))
        //         .execute();
    }
}