package org.luvx.api;

import org.junit.Test;
import org.luvx.entity.User;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * 内省
 */
public class IntrospectorCaseTest {

    @Test
    public void run() throws IntrospectionException {
        // 获取Bean的所有属性的PropertyDescriptor
        BeanInfo bi = Introspector.getBeanInfo(User.class);
        PropertyDescriptor[] pd = bi.getPropertyDescriptors();

        // 指定属性的PropertyDescriptor
        PropertyDescriptor pdAge = new PropertyDescriptor("age", User.class);

        Method setMethod = pdAge.getReadMethod();
        Method getMethod = pdAge.getWriteMethod();
    }
}
