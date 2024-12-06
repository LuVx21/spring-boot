package org.luvx.boot.mybatis.mapper.common.utils;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.luvx.boot.mybatis.mapper.common.annotations.Ignore;
import org.luvx.boot.mybatis.mapper.common.annotations.Table;
import org.luvx.boot.mybatis.mapper.common.annotations.TableId;
import org.luvx.boot.mybatis.mapper.common.base.BaseMapper;
import org.luvx.boot.mybatis.mapper.common.query.Query;
import org.luvx.coding.common.util.MoreBeanUtils;
import org.springframework.core.ResolvableType;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProviderUtils {
    private static final Predicate<Field> validFieldFilter = field -> {
        Ignore ignore = field.getAnnotation(Ignore.class);
        return !"serialVersionUID".equals(field.getName()) && ignore == null;
    };

    @Getter
    @ToString
    @AllArgsConstructor
    public static class CacheEntry {
        String                pkField;
        String                pkColumn;
        String[]              allField;
        BiMap<String, String> fieldColumnMap;
    }

    public static final LoadingCache<Class<?>, CacheEntry> FIELD_COLUMN_CACHE = Caffeine.newBuilder()
            .maximumSize(500)
            .expireAfterAccess(Duration.ofDays(1))
            .recordStats()
            .build(ProviderUtils::allFieldAndColumnMap);

    /**
     * 获取实体类对象
     */
    public static Class<?> getEntityClass(ProviderContext context) {
        for (Type type : context.getMapperType().getGenericInterfaces()) {
            ResolvableType resolvableType = ResolvableType.forType(type);

            if (resolvableType.getRawClass() == BaseMapper.class) {
                return resolvableType.getGeneric(0).getRawClass();
            }
        }
        return null;
    }

    /**
     * 对象转list, 按顺序存在字段名和字段值
     */
    public static List<Pair<String, Object>> beanToArray(Object object) {
        return MoreBeanUtils.beanToArrayByField(object, validFieldFilter);
    }

    /**
     * 对象转map
     * key: 属性名
     * value: 属性值
     */
    public static Map<String, Object> beanToMap(Object object) {
        return MoreBeanUtils.beanToMapByField(object, validFieldFilter);
    }

    /**
     * 获取实体类对应的表名称
     * 1. 使用了`Table`注解取配置的
     * 2. 未使用此注解取类名消驼峰
     *
     * @param clazz entity类对象
     * @return 表名
     */
    public static String getTableName(Class<?> clazz) {
        Table annotation = clazz.getAnnotation(Table.class);
        return annotation != null ? annotation.value() : toUnderscore(clazz.getSimpleName());
    }

    /**
     * 获取主键属性
     *
     * @return 主键
     */
    public static String getPrimaryKeyField(Class<?> clazz) {
        List<Field> fields = FieldUtils.getFieldsListWithAnnotation(clazz, TableId.class);
        Preconditions.checkState(fields.size() == 1, "不存在或存在多个主键配置");
        return fields.getFirst().getName();
    }

    /**
     * 获取实体对应表的主键(仅支持唯一主键)
     *
     * @return 主键字段名, 如`user_id`
     */
    public static String getPrimaryKeyColumn(Class<?> clazz) {
        List<Field> fields = FieldUtils.getFieldsListWithAnnotation(clazz, TableId.class);
        Preconditions.checkState(fields.size() == 1, "不存在或存在多个主键配置");
        Field field = fields.getFirst();
        TableId annotation = field.getAnnotation(TableId.class);
        String pk = annotation.value();
        if (StringUtils.isNotEmpty(pk)) {
            return pk;
        }
        return toUnderscore(field.getName());
    }

    /**
     * 获取实体内所有属性(排除序列化字段, Ignore修饰的字段)
     *
     * @return 类字段名称, 有序
     */
    public static String[] getAllFields(Class<?> clazz) {
        List<Field> allFieldsList = FieldUtils.getAllFieldsList(clazz);
        return allFieldsList.stream()
                .filter(validFieldFilter)
                .map(Field::getName)
                .toArray(String[]::new);
    }

    /**
     * 获取实体对应的所有表字段名
     *
     * @return 类字段对应表列名称, 有序
     */
    public static String[] getAllColumns(Class<?> clazz) {
        return Arrays.stream(getAllFields(clazz))
                .map(ProviderUtils::toUnderscore)
                .toArray(String[]::new);
    }

    public static CacheEntry allFieldAndColumnMap(Class<?> clazz) {
        String pkField = getPrimaryKeyField(clazz), pkColumn = getPrimaryKeyColumn(clazz);
        String[] allFields = getAllFields(clazz);
        Map<String, String> map = Arrays.stream(allFields)
                .collect(Collectors.toMap(s -> s, ProviderUtils::toUnderscore, (a, b) -> b));
        return new CacheEntry(pkField, pkColumn, allFields, HashBiMap.create(map));
    }

    /**
     * 获取查询列
     * user_name as userName
     */
    public static String[] getSelectColumns(Class<?> clazz) {
        CacheEntry cacheEntry = FIELD_COLUMN_CACHE.get(clazz);
        String[] allField = cacheEntry.getAllField();
        Map<String, String> fieldColumnMap = cacheEntry.getFieldColumnMap();
        return Arrays.stream(allField)
                .map(n -> "`" + fieldColumnMap.get(n) + "` as " + n)
                .toArray(String[]::new);
    }

    /**
     * where语句
     *
     * @param query
     * @param clazz
     * @return
     */
    public static String[] getWheres(Query<?> query, Class<?> clazz) {
        List<String> wheres = new ArrayList<>();
        Map<String, Object> queryObj = beanToMap(query);
        for (Method method : clazz.getMethods()) {
            String name = method.getName();
            if (name.length() <= 3
                    || !name.startsWith("get")
                    || !Character.isUpperCase(name.charAt(3))
            ) {
                continue;
            }

            String fieldName = (char) (name.charAt(3) - 'A' + 'a') + name.substring(4);
            if (queryObj.get(fieldName) != null) {
                wheres.add("`" + toUnderscore(fieldName) + "` = #{" + fieldName + "}");
            }
            if (method.getReturnType().equals(String.class) && queryObj.get(fieldName + "Like") != null) {
                wheres.add("`" + toUnderscore(fieldName) + "` like CONCAT('%', #{" + fieldName + "Like}, '%')");
            }
        }
        return wheres.toArray(new String[]{});
    }

    /**
     * 消驼峰:属性名 -> 表字段名
     * 例: userName -> user_name
     *
     * @param name
     * @return
     */
    public static String toUnderscore(String name) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char ch = name.charAt(i);
            if (Character.isUpperCase(ch)) {
                if (!sb.isEmpty()) {
                    sb.append('_');
                }
                sb.append((char) (ch - 'A' + 'a'));
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    /**
     * getter属性名
     *
     * @param clazz
     * @return
     */
    @Deprecated
    public static String[] getReadFields(Class<?> clazz) {
        return getVariables(clazz, new String[]{"is", "get"});
    }

    /**
     * setter属性名
     *
     * @param clazz
     * @return
     */
    @Deprecated
    public static String[] getWriteFields(Class<?> clazz) {
        return getVariables(clazz, new String[]{"set"});
    }

    /**
     * getter/setter属性名
     *
     * @param clazz
     * @param prefixes
     * @return
     */
    @Deprecated
    private static String[] getVariables(Class<?> clazz, String[] prefixes) {
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
}


