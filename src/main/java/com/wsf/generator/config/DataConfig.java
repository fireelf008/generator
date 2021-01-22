package com.wsf.generator.config;

import com.wsf.generator.modular.system.dao.ConfigDao;
import com.wsf.generator.modular.system.dao.DictTypeDao;
import com.wsf.generator.modular.system.entity.Config;
import com.wsf.generator.modular.system.entity.Dict;
import com.wsf.generator.modular.system.entity.DictType;
import com.wsf.generator.utils.ApplicationContextUtils;
import com.wsf.generator.utils.RedissonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DataConfig implements CommandLineRunner {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private DictTypeDao dictTypeDao;

    @Autowired
    private ConfigDao configDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void run(String... args) {
        ApplicationContextUtils.setApplicationContext(applicationContext);

        log.info("--------------------开始加载字典--------------------");
        List<DictType> dictTypeList = this.dictTypeDao.findAll();
        Map<String, Map<String, String>> dictTypeMap = new HashMap<>();
        for (DictType dictType : dictTypeList) {
            Map<String, String> dictMap = new HashMap<>();
            for (Dict dict : dictType.getDictList()) {
                dictMap.put(dict.getDictCode(), dict.getDictName());
            }
            dictTypeMap.put(dictType.getTypeCode(), dictMap);
        }
        RedissonUtils.putObject("dict", dictTypeMap);

        log.info("--------------------开始加载Config--------------------");
        List<Config> configList = this.configDao.findAll();
        Map<String, Config> configMap = configList.stream().filter(con -> StringUtils.isNotEmpty(con.getConfKey())).collect(Collectors.toMap(con -> con.getConfKey(), con -> con));
        RedissonUtils.putObject("config", configMap);
    }
}
