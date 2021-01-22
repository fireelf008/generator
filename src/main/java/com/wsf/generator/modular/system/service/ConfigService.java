package com.wsf.generator.modular.system.service;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wsf.generator.modular.system.dao.ConfigDao;
import com.wsf.generator.modular.system.entity.*;
import com.wsf.generator.utils.DateUtils;
import com.wsf.generator.utils.QueryDslUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConfigService {

    @Autowired
    private JPAQueryFactory factory;

    @Autowired
    private ConfigDao configDao;

    /**
     * 分页查询配置列表
     * @param pageRequest
     * @return
     */
    public Page<Config> findAll(QPageRequest pageRequest, String confName, String createTime) {
        QConfig qConfig = QConfig.config;

        Predicate where = qConfig.id.isNotNull();
        if (ObjectUtils.isNotEmpty(confName)) {
            where = ExpressionUtils.and(where, qConfig.confName.like("%" +confName + "%"));
        }
        if (StringUtils.isNotEmpty(createTime)) {
            Predicate createTimePredicate = qConfig.createTime.between(DateUtils.getTimesStart(createTime), DateUtils.getTimesEnd(createTime));
            where = ExpressionUtils.and(where, createTimePredicate);
        }
        QueryResults<Config> configList = this.factory
                .selectFrom(qConfig)
                .where(where)
                .orderBy(QueryDslUtils.getOrderSpecifier(pageRequest.getSort()))
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetchResults();
        Page<Config> configPage = new PageImpl<>(configList.getResults(), pageRequest, configList.getTotal());
        return configPage;
    }
    /**
     * 新增或修改数据
     * @param config
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Config config) {
        User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        if (null == config.getId()) {
            config.setCreateName(user.getRealName());
            config.setUpdateName(user.getRealName());
            this.configDao.save(config);
        } else {
            Config oldRes = this.configDao.findById(config.getId()).orElse(null);
            oldRes.setConfName(config.getConfName());
            oldRes.setConfValue(config.getConfValue());
            oldRes.setUpdateName(user.getRealName());
            this.configDao.save(oldRes);
        }
    }

    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    public Config findById(Long id) {
        return this.configDao.findById(id).orElse(null);
    }

    /**
     * 按id删除
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        QConfig qConfig = QConfig.config;
        this.factory
                .delete(qConfig)
                .where(qConfig.id.eq(id))
                .execute();
    }
}
