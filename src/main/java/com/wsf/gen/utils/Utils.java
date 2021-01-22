package com.wsf.gen.utils;

import com.wsf.gen.config.FieldColumn;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    /**
     * 首字母转小写
     * @param s
     * @return
     */
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    /**
     * 首字母转大写
     * @param s
     * @return
     */
    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    /**
     * 获取实体的所有属性，包括从父类继承的属性，排除不包含@Column的属性
     * @param clazz
     * @return
     */
    public static List<Field> getFieldList(Class clazz) {
        List<Field> fieldList = new ArrayList<>();

        while (ObjectUtils.isNotEmpty(clazz)) {
            fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }

        fieldList = fieldList
                .stream()
                .filter(field -> ObjectUtils.isNotEmpty(field.getAnnotation(Column.class)))
                .collect(Collectors.toList());

        return fieldList;
    }

    /**
     * 获取属性与对应get方法名
     * @param fieldList
     * @return
     */
    public static LinkedHashMap<String, String> getFieldNameAndMethodNameMap(List<Field> fieldList) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>(fieldList.size());
        for (Field field : fieldList) {
            map.put(field.getName(), new StringBuilder("get").append(Utils.toUpperCaseFirstOne(field.getName())).toString());
        }
        return map;
    }

    /**
     * 获取属性与对应get方法名
     * @param fieldList
     * @return
     */
    public static LinkedHashMap<String, FieldColumn> getFieldColumnMap(List<Field> fieldList) {
        LinkedHashMap<String, FieldColumn> map = new LinkedHashMap<>(fieldList.size());
        for (Field field : fieldList) {
            FieldColumn fieldColumn = new FieldColumn();
            fieldColumn.setType(field.getType().getSimpleName());
            fieldColumn.setName(field.getName());
            fieldColumn.setMethodName(new StringBuilder("get").append(Utils.toUpperCaseFirstOne(field.getName())).toString());

            if (ObjectUtils.isNotEmpty(field.getAnnotation(ApiModelProperty.class)) && ObjectUtils.isNotEmpty(field.getAnnotation(ApiModelProperty.class).value())) {
                fieldColumn.setComment(field.getAnnotation(ApiModelProperty.class).value());
            } else {
                fieldColumn.setComment(field.getName());
            }
            map.put(field.getName(), fieldColumn);
        }
        return map;
    }

    /**
     * 获取主键属性名
     * @param clazz
     * @return
     */
    public static String getIdName(Class clazz) {
        String idName = null;
        List<Field> fieldList = Utils.getFieldList(clazz);
        for (Field field : fieldList) {
            if (ObjectUtils.isNotEmpty(field.getAnnotation(Id.class))) {
                idName = field.getName();
                break;
            }
        }
        return idName;
    }

    /**
     * 获取主键类型名
     * @param clazz
     * @return
     */
    public static String getIdType(Class clazz) {
        String idType = null;
        List<Field> fieldList = Utils.getFieldList(clazz);
        for (Field field : fieldList) {
            if (ObjectUtils.isNotEmpty(field.getAnnotation(Id.class))) {
                idType = field.getType().getSimpleName();
                break;
            }
        }
        return idType;
    }
}
