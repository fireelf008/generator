package com.wsf.gen.core;

import com.wsf.gen.config.Config;

public class BaseGenerator {

    public static void main(String[] args) {

        Config config = new Config();
        config.setEntityPath("com.wsf.generator.modular.test.entity.Test");
        config.init();

        DaoGenerator daoGenerator = new DaoGenerator(config);
        daoGenerator.create();

        ServiceGenerator serviceGenerator = new ServiceGenerator(config);
        serviceGenerator.create();

        ControllerGenerator controllerGenerator = new ControllerGenerator(config);
        controllerGenerator.create();

        ListPageGenerator listPageGenerator = new ListPageGenerator(config);
        listPageGenerator.create();

        ListJsGenerator listJsGenerator = new ListJsGenerator(config);
        listJsGenerator.create();

        ListCssGenerator listCssGenerator = new ListCssGenerator(config);
        listCssGenerator.create();

        AddPageGenerator addPageGenerator = new AddPageGenerator(config);
        addPageGenerator.create();

        AddJsGenerator addJsGenerator = new AddJsGenerator(config);
        addJsGenerator.create();

        AddCssGenerator addCssGenerator = new AddCssGenerator(config);
        addCssGenerator.create();

        EditPageGenerator editPageGenerator = new EditPageGenerator(config);
        editPageGenerator.create();

        EditJsGenerator editJsGenerator = new EditJsGenerator(config);
        editJsGenerator.create();

        EditCssGenerator editCssGenerator = new EditCssGenerator(config);
        editCssGenerator.create();
    }
}
