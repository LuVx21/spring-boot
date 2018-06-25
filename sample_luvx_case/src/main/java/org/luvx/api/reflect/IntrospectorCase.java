package org.luvx.api.reflect;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * @author renxie
 */
public class IntrospectorCase {


    public static void setProperty(Object obj, String propertyName, Object value) throws Exception {
        PropertyDescriptor pd = new PropertyDescriptor(propertyName, obj.getClass());
        Method setMethod = pd.getWriteMethod();
        setMethod.invoke(obj, value);
    }

    public static Object getProperty(Object obj, String propertyName) throws Exception {
        PropertyDescriptor pd = new PropertyDescriptor(propertyName, obj.getClass());
        Method getMethod = pd.getReadMethod();
        return getMethod.invoke(obj);
    }

}
