package org.luvx.mapper.anno;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.luvx.common.entity.User;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UserSqlProvider {

    public String insertSelective(User record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("user");

        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=BIGINT}");
        }

        if (record.getUserName() != null) {
            sql.VALUES("user_name", "#{userName,jdbcType=VARCHAR}");
        }

        if (record.getPassword() != null) {
            sql.VALUES("password", "#{password,jdbcType=VARCHAR}");
        }

        if (record.getAge() != null) {
            sql.VALUES("age", "#{age,jdbcType=INTEGER}");
        }

        return sql.toString();
    }

    public String insertList(@Param("list") Collection<User> records) {
        SQL sql = new SQL();
        sql.INSERT_INTO("user");

        int i = 0;
        sql.VALUES("id", "#{list[" + i + "].id,jdbcType=BIGINT}");
        sql.VALUES("user_name", "#{list[" + i + "].userName,jdbcType=VARCHAR}");
        sql.VALUES("password", "#{list[" + i + "].password,jdbcType=VARCHAR}");
        sql.VALUES("age", "#{list[" + i + "].age,jdbcType=INTEGER}");

        StringBuilder sb = new StringBuilder(sql.toString());
        int size = records.size();
        for (i = i + 1; i < size; i++) {
            sb.append(",(")
                    .append("#{list[" + i + "].id,jdbcType=BIGINT}").append(",")
                    .append("#{list[" + i + "].userName,jdbcType=VARCHAR}").append(",")
                    .append("#{list[" + i + "].password,jdbcType=VARCHAR}").append(",")
                    .append("#{list[" + i + "].age,jdbcType=INTEGER}")
                    .append(")");
        }

        return sb.append(";").toString();
    }

    public String updateByPrimaryKeySelective(User record) {
        SQL sql = new SQL();
        sql.UPDATE("user");

        if (record.getUserName() != null) {
            sql.SET("user_name = #{userName,jdbcType=VARCHAR}");
        }

        if (record.getPassword() != null) {
            sql.SET("password = #{password,jdbcType=VARCHAR}");
        }

        if (record.getAge() != null) {
            sql.SET("age = #{age,jdbcType=INTEGER}");
        }

        sql.WHERE("id = #{id,jdbcType=BIGINT}");

        return sql.toString();
    }

    public String updateSelective(@Param("record") User record, @Param("target") User target) {
        SQL sql = new SQL();
        sql.UPDATE("user");

        if (target.getUserName() != null) {
            sql.SET("user_name = #{target.userName,jdbcType=VARCHAR}");
        }

        if (target.getPassword() != null) {
            sql.SET("password = #{target.password,jdbcType=VARCHAR}");
        }

        if (target.getAge() != null) {
            sql.SET("age = #{target.age,jdbcType=INTEGER}");
        }

        if (record.getUserName() != null) {
            sql.WHERE("user_name = #{record.userName,jdbcType=VARCHAR}");
        }

        if (record.getPassword() != null) {
            sql.WHERE("password = #{record.password,jdbcType=VARCHAR}");
        }

        if (record.getAge() != null) {
            sql.WHERE("age = #{record.age,jdbcType=INTEGER}");
        }

        return sql.toString();
    }

    public String updateByPrimaryKeyList(@Param("ids") Collection<Serializable> ids, @Param("record") User record) {
        SQL sql = new SQL();
        sql.UPDATE("user");

        if (record.getUserName() != null) {
            sql.SET("user_name = #{record.userName,jdbcType=VARCHAR}");
        }

        if (record.getPassword() != null) {
            sql.SET("password = #{record.password,jdbcType=VARCHAR}");
        }

        if (record.getAge() != null) {
            sql.SET("age = #{record.age,jdbcType=INTEGER}");
        }

        sql.WHERE("id in ("
                +
                IntStream.range(0, ids.size())
                        .mapToObj(i -> "#{ids[" + i + "],jdbcType=BIGINT}")
                        .collect(Collectors.joining(", "))
                + ")");

        return sql.toString();
    }

    public String updateSelectiveList(@Param("records") Collection<User> records, @Param("target") User target) {
        SQL sql = new SQL();
        sql.UPDATE("user");

        if (target.getUserName() != null) {
            sql.SET("user_name = #{target.userName,jdbcType=VARCHAR}");
        }

        if (target.getPassword() != null) {
            sql.SET("password = #{target.password,jdbcType=VARCHAR}");
        }

        if (target.getAge() != null) {
            sql.SET("age = #{target.age,jdbcType=INTEGER}");
        }

        int i = 0;
        for (User record : records) {
            sql.OR();
            if (record.getUserName() != null) {
                sql.WHERE("user_name = #{records[" + i + "].userName,jdbcType=VARCHAR}");
            }

            if (record.getPassword() != null) {
                sql.WHERE("password = #{records[" + i + "].password,jdbcType=VARCHAR}");
            }

            if (record.getAge() != null) {
                sql.WHERE("age = #{records[" + i + "].age,jdbcType=INTEGER}");
            }
            i++;
        }

        return sql.toString();
    }

    public String deleteSelective(User record) {
        SQL sql = new SQL();
        sql.DELETE_FROM("user");

        if (record.getUserName() != null) {
            sql.WHERE("user_name = #{userName,jdbcType=VARCHAR}");
        }

        if (record.getPassword() != null) {
            sql.WHERE("password = #{password,jdbcType=VARCHAR}");
        }

        if (record.getAge() != null) {
            sql.WHERE("age = #{age,jdbcType=INTEGER}");
        }

        return sql.toString();
    }


    public String deleteByPrimaryKeyList(@Param("ids") Collection<Serializable> ids) {
        SQL sql = new SQL();
        sql.DELETE_FROM("user");

        sql.WHERE("id in ("
                +
                IntStream.range(0, ids.size())
                        .mapToObj(i -> "#{ids[" + i + "],jdbcType=BIGINT}")
                        .collect(Collectors.joining(", "))
                + ")");
        return sql.toString();
    }

    public String deleteSelectiveList(@Param("records") Collection<User> records) {
        SQL sql = new SQL();
        sql.DELETE_FROM("user");

        int i = 0;
        for (User record : records) {
            sql.OR();
            if (record.getUserName() != null) {
                sql.WHERE("user_name = #{records[" + i + "].userName,jdbcType=VARCHAR}");
            }

            if (record.getPassword() != null) {
                sql.WHERE("password = #{records[" + i + "].password,jdbcType=VARCHAR}");
            }

            if (record.getAge() != null) {
                sql.WHERE("age = #{records[" + i + "].age,jdbcType=INTEGER}");
            }
            i++;
        }

        return sql.toString();
    }

    public String selectSelective(@Param("record") User record) {
        SQL sql = new SQL();
        sql.SELECT("id, user_name, password, age, update_time");
        sql.FROM("user");

        if (record.getUserName() != null) {
            sql.WHERE("user_name = #{record.userName,jdbcType=VARCHAR}");
        }

        if (record.getPassword() != null) {
            sql.WHERE("password = #{record.password,jdbcType=VARCHAR}");
        }

        if (record.getAge() != null) {
            sql.WHERE("age = #{record.age,jdbcType=INTEGER}");
        }

        return sql.toString();
    }

    public String selectByPrimaryKeyList(@Param("ids") Collection<Serializable> ids) {
        SQL sql = new SQL();
        sql.SELECT("id, user_name, password, age, update_time");
        sql.FROM("user");

        sql.WHERE("id in ("
                +
                IntStream.range(0, ids.size())
                        .mapToObj(i -> "#{ids[" + i + "],jdbcType=BIGINT}")
                        .collect(Collectors.joining(", "))
                + ")");
        return sql.toString();
    }

    public String selectSelectiveList(@Param("records") Collection<User> records) {
        SQL sql = new SQL();
        sql.SELECT("id, user_name, password, age, update_time");
        sql.FROM("user");

        int i = 0;
        for (User record : records) {
            sql.OR();
            if (record.getUserName() != null) {
                sql.WHERE("user_name = #{records[" + i + "].userName,jdbcType=VARCHAR}");
            }

            if (record.getPassword() != null) {
                sql.WHERE("password = #{records[" + i + "].password,jdbcType=VARCHAR}");
            }

            if (record.getAge() != null) {
                sql.WHERE("age = #{records[" + i + "].age,jdbcType=INTEGER}");
            }
            i++;
        }

        return sql.toString();
    }

}