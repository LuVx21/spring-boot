package org.luvx.entity;

import java.io.Serializable;

/**
 * 测试环境用bean
 * <p>
 * po的规范(Persistent Object:持久化对象)
 * 1. 公有类
 * 2. 私有属性
 * 3. 公有的getter与setter
 * 4. 不能使用final修饰
 * 5. 提供默认无参构造
 * 6. 如果是基本类型，就写它对应的包装类
 * 7. 一般都要实现java.io.Serializable
 */
public class Hello implements Serializable {

    private Integer id;
    private String word;
    private Double num;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Double getNum() {
        return num;
    }

    public void setNum(Double num) {
        this.num = num;
    }
}
