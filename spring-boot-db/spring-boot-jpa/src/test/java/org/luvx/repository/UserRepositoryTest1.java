package org.luvx.repository;

import com.alibaba.fastjson.JSON;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.luvx.ApplicationTests;
import org.luvx.entity.QArticle;
import org.luvx.entity.QUser;
import org.luvx.entity.User;
import org.luvx.entity.dto.UserArticleVo;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Slf4j
public class UserRepositoryTest1 extends ApplicationTests {
    @Resource
    UserRepository    userRepository;
    @Resource
    ArticleRepository articleRepository;
    @Resource
    JPAQueryFactory   queryFactory;

    @Test
    public void select1() {
        QUser user = QUser.user;
        Iterable<User> all = userRepository.findAll(user.id.eq(1L));
        Optional<User> one = userRepository.findOne(user.id.eq(1L));
    }

    @Test
    public void select() {
        QUser user = QUser.user;
        List<String> nameList = queryFactory.select(user.userName)
                .from(user)
                .where(user.id.eq(1L), user.password.eq("bar"))
                .fetch();
        log.info("a:{}", nameList);

        List<User> memberList = queryFactory.selectFrom(user)
                .where(user.id.eq(1L))
                .fetch();
        log.info("b:{}", JSON.toJSONString(memberList));

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

        User user1 = queryFactory
                .selectFrom(user)
                .fetchFirst();

        User user2 = queryFactory
                .selectFrom(user)
                .orderBy(user.id.asc())
                .offset(1L).limit(1L)
                .fetchOne();

        List<User> subList = queryFactory.selectFrom(user)
                .where(user.id.in(JPAExpressions.selectDistinct(article.userId).from(article)))
                .fetch();
    }

    @Test
    // @Transactional(rollbackFor = Exception.class)
    public void update() {
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