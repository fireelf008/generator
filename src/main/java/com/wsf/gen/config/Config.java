package com.wsf.gen.config;

import com.wsf.gen.utils.Utils;
import lombok.Data;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Data
public class Config implements Serializable {

    private String templatePath = "src/main/java/com/wsf/gen/template";
    private String entityPath;
    private Class clazz;
    private String idName;
    private String idType;
    private String idMethodName;
    private List<Field> fieldList = new ArrayList<>();
    private LinkedHashMap<String, FieldColumn> fieldColumnMap = new LinkedHashMap<>();
    private String entityPackage;
    private String entityName;
    private String varEntityName;
    private String qentityName;
    private String qvarEntityName;
    private String modularName;
    private String baseOutputPackage;
    private String baseOutputPath;
    private String daoPackage;
    private String daoName;
    private String varDaoName;
    private String daoOutputPath;
    private String daoFilePath;
    private String servicePackage;
    private String serviceName;
    private String varServiceName;
    private String serviceOutputPath;
    private String serviceFilePath;
    private String controllerPackage;
    private String controllerName;
    private String varControllerName;
    private String controllerOutputPath;
    private String controllerFilePath;
    private String pageOutputPath;
    private String jsOutputPath;
    private String cssOutputPath;
    private String listPageFilePath;
    private String addPageFilePath;
    private String editPageFilePath;
    private String listJsFilePath;
    private String addJsFilePath;
    private String editJsFilePath;
    private String listCssFilePath;
    private String addCssFilePath;
    private String editCssFilePath;

    public void init() {
        try {
            this.clazz = Class.forName(this.entityPath);
            this.entityPackage = this.clazz.getName().substring(0, clazz.getName().lastIndexOf("."));
            this.entityName = this.clazz.getName().substring(clazz.getName().lastIndexOf(".") + 1);
            this.varEntityName = Utils.toLowerCaseFirstOne(this.entityName);
            this.qentityName = new StringBuilder("Q").append(this.entityName).toString();
            this.qvarEntityName = new StringBuilder("q").append(this.entityName).toString();
            this.baseOutputPackage = this.entityPackage.substring(0, this.entityPackage.lastIndexOf("."));
            this.baseOutputPath = new StringBuilder("src\\main\\java\\").append(this.baseOutputPackage.replaceAll("\\.", "\\\\")).toString();
            this.modularName = this.baseOutputPackage.substring(this.baseOutputPackage.lastIndexOf(".") + 1);
            this.daoPackage = new StringBuilder(this.baseOutputPackage).append(".dao").toString();
            this.daoName = new StringBuilder(this.entityName).append("Dao").toString();
            this.varDaoName = new StringBuilder(this.varEntityName).append("Dao").toString();
            this.daoOutputPath = new StringBuilder(this.baseOutputPath).append("\\dao").toString();
            this.daoFilePath = new StringBuilder(this.daoOutputPath).append("\\").append(this.entityName).append("Dao.java").toString();
            this.servicePackage = new StringBuilder(this.baseOutputPackage).append(".service").toString();
            this.serviceName = new StringBuilder(this.entityName).append("Service").toString();
            this.varServiceName = new StringBuilder(this.varEntityName).append("Service").toString();
            this.serviceOutputPath = new StringBuilder(this.baseOutputPath).append("\\service").toString();
            this.serviceFilePath = new StringBuilder(this.serviceOutputPath).append("\\").append(this.entityName).append("Service.java").toString();
            this.controllerPackage = new StringBuilder(this.baseOutputPackage).append(".controller").toString();
            this.controllerName = new StringBuilder(this.entityName ).append("Controller").toString();
            this.varControllerName = new StringBuilder(this.varEntityName).append("Controller").toString();
            this.controllerOutputPath = new StringBuilder(this.baseOutputPath).append("\\controller").toString();
            this.controllerFilePath = new StringBuilder(this.controllerOutputPath).append("\\").append(this.entityName).append("Controller.java").toString();
            this.pageOutputPath = new StringBuilder("src\\main\\resources\\templates\\mng\\").append(modularName).toString();
            this.jsOutputPath = new StringBuilder("src\\main\\resources\\static\\js\\mng\\").append(modularName).toString();
            this.cssOutputPath = new StringBuilder("src\\main\\resources\\static\\css\\mng\\").append(modularName).toString();
            this.listPageFilePath = new StringBuilder(this.pageOutputPath).append("\\").append(this.varEntityName).append("_list.html").toString();
            this.addPageFilePath = new StringBuilder(this.pageOutputPath).append("\\").append(this.varEntityName).append("_add.html").toString();
            this.editPageFilePath = new StringBuilder(this.pageOutputPath).append("\\").append(this.varEntityName).append("_edit.html").toString();
            this.listJsFilePath = new StringBuilder(this.jsOutputPath).append("\\").append(this.varEntityName).append("_list.js").toString();
            this.addJsFilePath = new StringBuilder(this.jsOutputPath).append("\\").append(this.varEntityName).append("_add.js").toString();
            this.editJsFilePath = new StringBuilder(this.jsOutputPath).append("\\").append(this.varEntityName).append("_edit.js").toString();
            this.listCssFilePath = new StringBuilder(this.cssOutputPath).append("\\").append(this.varEntityName).append("_list.css").toString();
            this.addCssFilePath = new StringBuilder(this.cssOutputPath).append("\\").append(this.varEntityName).append("_add.css").toString();
            this.editCssFilePath = new StringBuilder(this.cssOutputPath).append("\\").append(this.varEntityName).append("_edit.css").toString();

            this.fieldList = Utils.getFieldList(this.clazz);
            this.fieldColumnMap = Utils.getFieldColumnMap(this.fieldList);
            this.idName = Utils.getIdName(clazz);
            this.idType = Utils.getIdType(clazz);
            this.idMethodName = new StringBuilder("get").append(Utils.toUpperCaseFirstOne(idName)).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
