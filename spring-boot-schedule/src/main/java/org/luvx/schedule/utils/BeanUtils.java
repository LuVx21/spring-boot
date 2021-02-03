package org.luvx.schedule.utils;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class BeanUtils {
    public static Field findField(Class<?> clazz, String name) {
        try {
            return clazz.getField(name);
        } catch (NoSuchFieldException ex) {
            return findDeclaredField(clazz, name);
        }
    }

    public static Field findDeclaredField(Class<?> clazz, String name) {
        try {
            return clazz.getDeclaredField(name);
        } catch (NoSuchFieldException ex) {
            if (clazz.getSuperclass() != null) {
                return findDeclaredField(clazz.getSuperclass(), name);
            }
            return null;
        }
    }

    public static Method findMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) {
        try {
            return clazz.getMethod(methodName, paramTypes);
        } catch (NoSuchMethodException ex) {
            return findDeclaredMethod(clazz, methodName, paramTypes);
        }
    }

    public static Method findDeclaredMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) {
        try {
            return clazz.getDeclaredMethod(methodName, paramTypes);
        } catch (NoSuchMethodException ex) {
            if (clazz.getSuperclass() != null) {
                return findDeclaredMethod(clazz.getSuperclass(), methodName, paramTypes);
            }
            return null;
        }
    }

    public static Object getProperty(Object obj, String name) throws NoSuchFieldException {
        Object value = null;
        Field field = findField(obj.getClass(), name);
        if (field == null) {
            throw new NoSuchFieldException("no such field [" + name + "]");
        }
        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        try {
            value = field.get(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        field.setAccessible(accessible);
        return value;
    }

    public static void setProperty(Object obj, String name, Object value) throws NoSuchFieldException {
        Field field = findField(obj.getClass(), name);
        if (field == null) {
            throw new NoSuchFieldException("no such field [" + name + "]");
        }
        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        try {
            field.set(obj, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        field.setAccessible(accessible);
    }

    public static Map<String, Object> obj2Map(Object obj, Map<String, Object> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        if (obj != null) {
            try {
                Class<?> clazz = obj.getClass();
                do {
                    Field[] fields = clazz.getDeclaredFields();
                    for (Field field : fields) {
                        int mod = field.getModifiers();
                        if (Modifier.isStatic(mod)) {
                            continue;
                        }
                        boolean accessible = field.isAccessible();
                        field.setAccessible(true);
                        map.put(field.getName(), field.get(obj));
                        field.setAccessible(accessible);
                    }
                    clazz = clazz.getSuperclass();
                } while (clazz != null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return map;
    }

    /**
     * 获得父类集合，包含当前class
     *
     * @param clazz
     * @return
     */
    public static List<Class<?>> getSuperclassList(Class<?> clazz) {
        List<Class<?>> clazzes = new ArrayList<>(3);
        clazzes.add(clazz);
        clazz = clazz.getSuperclass();
        while (clazz != null) {
            clazzes.add(clazz);
            clazz = clazz.getSuperclass();
        }
        return Collections.unmodifiableList(clazzes);
    }

}
