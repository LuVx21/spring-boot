package org.luvx.pattern.Prototype;


import java.io.Serializable;

/**
 * 模拟周报中的附件
 */

public class Picture implements Serializable{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void download() {
        System.out.println("下载附件，文件名为" + name);
    }
}
