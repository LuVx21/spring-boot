package org.luvx.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.luvx.common.annotations.Ignore;
import org.luvx.common.annotations.Table;
import org.luvx.common.base.BaseMapper;
import org.luvx.common.query.Query;
import org.springframework.core.ResolvableType;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: org.luvx.common.utils
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/5/27 19:09
 */
public class ProviderUtils {
    /**
     * to json 对象
     *
     * @param object
     * @return
     */
    public static JSONObject parseObject(Object object) {
        if (object == null) {
            return new JSONObject();
        }
        return JSON.parseObject(JSON.toJSONString(object));
    }

    public static Class getEntityClass(ProviderContext context) {
        for (Type type : context.getMapperType().getGenericInterfaces()) {
            ResolvableType resolvableType = ResolvableType.forType(type);

            if (resolvableType.getRawClass() == BaseMapper.class
                // || resolvableType.getRawClass() == DODao.class
                // || resolvableType.getRawClass() == VODao.class
            ) {
                return resolvableType.getGeneric(0).getRawClass();
            }
        }
        return null;
    }

    /**
     * 获取查询列
     *
     * @param clazz
     * @return
     */
    public static String[] getColumns(Class clazz) {
        List<String> columns = new ArrayList<>();
        for (String variableName : getWriteVariables(clazz)) {
            columns.add(String.format("`%s` as `%s`", conversionName(variableName), variableName));
        }
        return columns.toArray(new String[]{});
    }

    /**
     * 1. 使用了`Table`注解
     * 2. 未使用此注解
     *
     * @param clazz entity类对象
     * @return
     */
    public static String getTableName(Class clazz) {
        Table annotation = (Table) clazz.getAnnotation(Table.class);
        if (annotation != null) {
            return String.format("`%s`", annotation.value());
        }
        return String.format("`%s`", conversionName(clazz.getSimpleName()));
    }

    /**
     * where语句
     *
     * @param query
     * @param clazz
     * @return
     */
    public static String[] getWheres(Query query, Class clazz) {
        List<String> wheres = new ArrayList<>();
        JSONObject queryObj = parseObject(query);
        for (Method method : clazz.getMethods()) {
            String name = method.getName();
            if (name.length() > 3 && name.startsWith("get")
                    && name.charAt(3) >= 'A' && name.charAt(3) <= 'Z') {
                String fieldName = (char) (name.charAt(3) - 'A' + 'a') + name.substring(4);
                if (queryObj.get(fieldName) != null) {
                    wheres.add(String.format("`%s`=#{%s}", conversionName(fieldName), fieldName));
                }
                if (method.getReturnType().equals(String.class)
                        && queryObj.get(fieldName + "Like") != null) {
                    wheres.add(String.format("`%s` like CONCAT('%%',#{%sLike}, '%%')", conversionName(fieldName), fieldName));
                }
            }
        }
        return wheres.toArray(new String[]{});
    }

    /**
     * getter属性名
     *
     * @param clazz
     * @return
     */
    public static String[] getReadVariables(Class clazz) {
        return getVariables(clazz, new String[]{"is", "get"});
    }

    /**
     * setter属性名
     *
     * @param clazz
     * @return
     */
    public static String[] getWriteVariables(Class clazz) {
        return getVariables(clazz, new String[]{"set"});
    }

    /**
     * getter/setter属性名
     *
     * @param clazz
     * @param prefixes
     * @return
     */
    private static String[] getVariables(Class clazz, String[] prefixes) {
        List<String> variables = new ArrayList<>();
        for (Method method : clazz.getMethods()) {
            Ignore annotation = method.getAnnotation(Ignore.class);
            if (annotation != null) {
                continue;
            }

            String name = method.getName();
            for (String prefix : prefixes) {
                int length = prefix.length();
                if (name.length() > length
                        && name.startsWith(prefix)
                        && name.charAt(length) >= 'A'
                        && name.charAt(length) <= 'Z') {
                    String variableName = (char) (name.charAt(length) - 'A' + 'a') + name.substring(length + 1);
                    variables.add(variableName);
                    break;
                }
            }
        }
        return variables.toArray(new String[]{});
    }

    /**
     * 属性名 -> 表字段名
     * 例: userName -> user_name
     *
     * @param name
     * @return
     */
    public static String conversionName(String name) {
        char[] chars = name.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (char ch : chars) {
            if (ch >= 'A' && ch <= 'Z') {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append('_');
                }
                stringBuilder.append((char) (ch - 'A' + 'a'));
            } else {
                stringBuilder.append(ch);
            }
        }
        return stringBuilder.toString();
    }
}


