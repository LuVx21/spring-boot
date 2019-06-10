package org.luvx.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.luvx.common.annotations.Ignore;
import org.luvx.common.annotations.Table;
import org.luvx.common.annotations.TableId;
import org.luvx.common.base.BaseMapper;
import org.luvx.common.query.Query;
import org.springframework.core.ResolvableType;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        return JSON.parseObject(
                JSON.toJSONString(object,
                        SerializerFeature.WriteMapNullValue,
                        SerializerFeature.QuoteFieldNames,
                        SerializerFeature.DisableCircularReferenceDetect)
        );
    }

    /**
     * 获取实体类对象
     *
     * @param context
     * @return
     */
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
     * 获取实体类对应的表名称
     * 1. 使用了`Table`注解取配置的
     * 2. 未使用此注解取类名消驼峰
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
     * 获取主键属性
     *
     * @param clazz
     * @return
     */
    public static String getPrimaryKeyField(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            TableId tableId = field.getAnnotation(TableId.class);
            if (tableId != null) {
                return field.getName();
            }
        }
        throw new RuntimeException("无法获取实体对应的主键信息");
    }

    /**
     * 获取实体对应表的主键(仅支持唯一主键)
     *
     * @param clazz
     * @return 主键字段名, 如`user_id`
     */
    public static String getPrimaryKey(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            TableId tableId = field.getAnnotation(TableId.class);
            if (tableId != null) {
                String pk = tableId.value();
                if (StringUtils.isEmpty(pk)) {
                    pk = conversionName(field.getName());
                }
                return String.format("`%s`", pk);
            }
        }
        throw new RuntimeException("无法获取实体对应的主键信息");
    }

    /**
     * 获取查询列
     *
     * @param clazz
     * @return
     */
    public static String[] getSelectColumns(Class clazz) {
        List<String> columns = new ArrayList<>();
        for (String variableName : getWriteFields(clazz)) {
            columns.add(String.format("`%s` as `%s`", conversionName(variableName), variableName));
        }
        return columns.toArray(new String[]{});
    }

    public static String[] getColumns(Class clazz) {
        List<String> columns = new ArrayList<>();
        for (String variableName : getWriteFields(clazz)) {
            columns.add(String.format("`%s`", conversionName(variableName)));
        }
        return columns.toArray(new String[]{});
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
    public static String[] getReadFields(Class clazz) {
        return getVariables(clazz, new String[]{"is", "get"});
    }

    /**
     * setter属性名
     *
     * @param clazz
     * @return
     */
    public static String[] getWriteFields(Class clazz) {
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
     * 消驼峰:属性名 -> 表字段名
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

    /**
     * 属性名-字段名对应map
     *
     * @param clazz
     * @return
     */
    public static Map<String, String> nameMap(Class<?> clazz) {
        // 使用guava的双向map
        BiMap<String, String> biMap = HashBiMap.create();
        BiMap<String, String> map = ImmutableBiMap.of("foo", "bar", "A", "a");
        return null;
    }

    /**
     * 获取实体内所有属性(排除序列化字段, Ignore修饰的字段)
     * 1. 直接获取属性字段
     * 2. 获取所有的set方法, 间接获取属性字段
     */


}


