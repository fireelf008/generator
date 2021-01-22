package com.wsf.generator.modular.system.service;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wsf.generator.modular.system.dao.ResourceDao;
import com.wsf.generator.modular.system.dao.RoleResourceDao;
import com.wsf.generator.modular.system.entity.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResourceService {

    @Autowired
    private JPAQueryFactory factory;

    @Autowired
    private ResourceDao resourceDao;

    @Autowired
    private RoleResourceDao roleResourceDao;

    /**
     * 分页查询权限列表
     * @param pageRequest
     * @return
     */
    public Page<Resource> findAll(PageRequest pageRequest, Resource resource) {
        QResource qResource = QResource.resource;

        Predicate where = qResource.id.isNotNull();
        if (ObjectUtils.isNotEmpty(resource.getResName())) {
            where = ExpressionUtils.and(where, qResource.resName.like( "%" + resource.getResName() + "%"));
        }
        if (ObjectUtils.isNotEmpty(resource.getType())) {
            where = ExpressionUtils.and(where, qResource.type.eq(resource.getType()));
        }
        return this.resourceDao.findAll(where, pageRequest);
    }

    /**
     * 查询所有权限
     * @return
     */
    public List<Resource> findAll() {
        return this.resourceDao.findAll();
    }

    /**
     * 按id查询数据
     * @param id
     * @return
     */
    public Resource findById(Long id) {
        return this.resourceDao.findById(id).orElse(null);
    }

    /**
     * 按id删除数据
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        QResource qResource = QResource.resource;
        QRoleResource qRoleResource = QRoleResource.roleResource;

        this.factory
                .delete(qResource)
                .where(qResource.id.eq(id))
                .execute();
        this.factory
                .delete(qRoleResource)
                .where(qRoleResource.resId.eq(id))
                .execute();
    }

    /**
     * 新增或修改数据
     * @param resource
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Resource resource) {
        User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        if (null == resource.getId()) {
            resource.setSort(null == resource.getSort() ? 0 : resource.getSort());
            resource.setCreateName(user.getRealName());
            resource.setUpdateName(user.getRealName());
            this.resourceDao.save(resource);
        } else {
            Resource oldRes = this.resourceDao.findById(resource.getId()).orElse(null);
            oldRes.setResName(resource.getResName());
            oldRes.setUrl(resource.getUrl());
            oldRes.setSort(null == resource.getSort() ? oldRes.getSort() : resource.getSort());
            oldRes.setIcon(resource.getIcon());
            oldRes.setType(resource.getType());
            oldRes.setPid(resource.getPid());
            oldRes.setPcode(resource.getPcode());
            oldRes.setPname(resource.getPname());
            oldRes.setUpdateName(user.getRealName());
            this.resourceDao.save(oldRes);
        }
    }
}
