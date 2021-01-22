package com.wsf.generator.modular.test.service;

import com.wsf.generator.modular.test.dao.TestDao;
import com.wsf.generator.modular.test.entity.QTest;
import com.wsf.generator.modular.test.entity.Test;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TestService {

    @Autowired
    private JPAQueryFactory factory;

    @Autowired
    private TestDao testDao;

    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Test test) {
        this.testDao.save(test);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(List<Test> testList) {
        this.testDao.saveAll(testList);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        this.testDao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(List<Long> idList) {
        QTest qTest = QTest.test;
        this.factory
                .delete(qTest)
                .where(qTest.id.in(idList));
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Test test) {
        this.deleteById(test.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Test> testList) {
        List<Long> idList = testList.stream().map(Test::getId).collect(Collectors.toList());
        this.deleteById(idList);
    }

    public Test findById(Long id) {
        return this.testDao.findById(id).orElse(null);
    }

    public List<Test> findAll() {
        return this.testDao.findAll();
    }

    public List<Test> findAll(Sort sort) {
        return this.testDao.findAll(sort);
    }

    public Page<Test> findAll(PageRequest pageRequest) {
        return this.testDao.findAll(pageRequest);
    }

    public Page<Test> findAll(PageRequest pageRequest, Test test) {
        QTest qTest = QTest.test;

        Predicate where = qTest.id.isNotNull();
        if (ObjectUtils.isNotEmpty(test.getId())) {
            where = ExpressionUtils.and(where, qTest.id.eq(test.getId()));
        }
        if (ObjectUtils.isNotEmpty(test.getUsername())) {
            where = ExpressionUtils.and(where, qTest.username.like("%" + test.getUsername() +"%"));
        }
        if (ObjectUtils.isNotEmpty(test.getPassword())) {
            where = ExpressionUtils.and(where, qTest.password.like("%" + test.getPassword() +"%"));
        }
        if (ObjectUtils.isNotEmpty(test.getSalt())) {
            where = ExpressionUtils.and(where, qTest.salt.like("%" + test.getSalt() +"%"));
        }
        if (ObjectUtils.isNotEmpty(test.getRealName())) {
            where = ExpressionUtils.and(where, qTest.realName.like("%" + test.getRealName() +"%"));
        }
        if (ObjectUtils.isNotEmpty(test.getBirthday())) {
            where = ExpressionUtils.and(where, qTest.birthday.eq(test.getBirthday()));
        }
        if (ObjectUtils.isNotEmpty(test.getSex())) {
            where = ExpressionUtils.and(where, qTest.sex.eq(test.getSex()));
        }
        if (ObjectUtils.isNotEmpty(test.getRegTime())) {
            where = ExpressionUtils.and(where, qTest.regTime.eq(test.getRegTime()));
        }
        if (ObjectUtils.isNotEmpty(test.getRegIp())) {
            where = ExpressionUtils.and(where, qTest.regIp.like("%" + test.getRegIp() +"%"));
        }
        if (ObjectUtils.isNotEmpty(test.getPhone())) {
            where = ExpressionUtils.and(where, qTest.phone.like("%" + test.getPhone() +"%"));
        }
        if (ObjectUtils.isNotEmpty(test.getLastTime())) {
            where = ExpressionUtils.and(where, qTest.lastTime.eq(test.getLastTime()));
        }
        if (ObjectUtils.isNotEmpty(test.getLastIp())) {
            where = ExpressionUtils.and(where, qTest.lastIp.like("%" + test.getLastIp() +"%"));
        }
        if (ObjectUtils.isNotEmpty(test.getAdmin())) {
            where = ExpressionUtils.and(where, qTest.admin.eq(test.getAdmin()));
        }
        if (ObjectUtils.isNotEmpty(test.getRemark())) {
            where = ExpressionUtils.and(where, qTest.remark.like("%" + test.getRemark() +"%"));
        }
        if (ObjectUtils.isNotEmpty(test.getCreateName())) {
            where = ExpressionUtils.and(where, qTest.createName.like("%" + test.getCreateName() +"%"));
        }
        if (ObjectUtils.isNotEmpty(test.getCreateTime())) {
            where = ExpressionUtils.and(where, qTest.createTime.eq(test.getCreateTime()));
        }
        if (ObjectUtils.isNotEmpty(test.getUpdateName())) {
            where = ExpressionUtils.and(where, qTest.updateName.like("%" + test.getUpdateName() +"%"));
        }
        if (ObjectUtils.isNotEmpty(test.getUpdateTime())) {
            where = ExpressionUtils.and(where, qTest.updateTime.eq(test.getUpdateTime()));
        }
        if (ObjectUtils.isNotEmpty(test.getEnable())) {
            where = ExpressionUtils.and(where, qTest.enable.eq(test.getEnable()));
        }
        if (ObjectUtils.isNotEmpty(test.getVersion())) {
            where = ExpressionUtils.and(where, qTest.version.eq(test.getVersion()));
        }
        return this.testDao.findAll(where, pageRequest);
    }
}