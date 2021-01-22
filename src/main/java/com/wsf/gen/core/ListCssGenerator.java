package com.wsf.gen.core;

import com.wsf.gen.config.Config;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ListCssGenerator {

    private Config config;

    public ListCssGenerator(Config config) {
        this.config = config;
    }

    public void create() {
        try {
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
            configuration.setDirectoryForTemplateLoading(new File(config.getTemplatePath()));

            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("config", config);

            File dir = new File(config.getCssOutputPath());
            if (!dir.exists()) {
                FileUtils.forceMkdir(dir);
            }

            Template template = configuration.getTemplate("list_css.ftl");
            File file = new File(config.getListCssFilePath());
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            template.process(dataMap, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
