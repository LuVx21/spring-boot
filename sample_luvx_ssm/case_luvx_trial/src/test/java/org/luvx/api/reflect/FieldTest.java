package org.luvx.api.reflect;

import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;


public class FieldTest {


    @Test
    public void run01() throws Exception {
        Class[] clazzs = InnerClass.class.getDeclaredClasses();
        Class clazz = clazzs[0];

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            System.out.println(field);
            System.out.println(field.getGenericType());
            System.out.println(field.getModifiers());
            System.out.println(Modifier.toString(field.getModifiers()));
        }
    }

    @Test
    public void run02() throws Exception {
        Class[] clazzs = InnerClass.class.getDeclaredClasses();
        // 获取类Peach
        Class clazz = clazzs[1];
        System.out.println(clazz);

        Field[] fields = clazz.getDeclaredFields();
        // 只能获取静态属性
        for (Field field : fields) {
            // private属性需设置访问
            field.setAccessible(true);
            Object o = field.get(clazz);
            System.out.println("属性名:" + field.getName() + " 属性值:" + o.toString());
        }
    }

    @Test
    public void run03() throws Exception {
        InnerClass.Watermelon watermelon = new InnerClass.Watermelon();
        watermelon.name = "new Watermelon";

        // 实例化对象的非静态属性也可以被访问
        Class clazz = watermelon.getClass();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object o = field.get(watermelon);
            System.out.println("属性名:" + field.getName() + " 属性值:" + o.toString());
        }

    }
}
