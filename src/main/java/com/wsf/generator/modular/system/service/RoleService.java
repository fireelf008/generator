package com.wsf.generator.modular.system.service;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wsf.generator.modular.system.dao.ResourceDao;
import com.wsf.generator.modular.system.dao.RoleDao;
import com.wsf.generator.modular.system.dao.RoleResourceDao;
import com.wsf.generator.modular.system.dao.UserRoleDao;
import com.wsf.generator.modular.system.entity.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    private JPAQueryFactory factory;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private ResourceDao resourceDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private RoleResourceDao roleResourceDao;

    public List<Role> findAll() {
        return this.roleDao.findAll();
    }

    /**
     * 分页查询权限列表
     * @param pageRequest
     * @param role
     * @return
     */
    public Page<Role> findAll(PageRequest pageRequest, Role role) {
        QRole qRole = QRole.role;

        Predicate where = qRole.id.isNotNull();
        if (ObjectUtils.isNotEmpty(role.getRoleName())) {
            where = ExpressionUtils.and(where, qRole.roleName.like("%" + role.getRoleName() + "%"));
        }
        return this.roleDao.findAll(where, pageRequest);
    }

    /**
     * 查询所有权限，以及按角色查询权限
     * @return
     */
    public List<Resource> findAllResAndRoleRes(Long roleId) {
        List<Resource> resourceList = this.resourceDao.findAll();
        List<RoleResource> roleResourceList = this.roleResourceDao.findByRoleId(roleId);
        for (Resource resource : resourceList) {
            for (RoleResource roleResource : roleResourceList) {
                if (resource.getId().equals(roleResource.getResId())) {
                    resource.setChecked(true);
                    continue;
                }
            }
        }

        Resource rootResource = new Resource();
        rootResource.setId(0L);
        rootResource.setResName("全部");
        rootResource.setResCode("root");
        if (null != roleResourceList && !roleResourceList.isEmpty() && 0 != roleResourceList.size()) {
            rootResource.setChecked(true);
        }
        resourceList.add(rootResource);

        return resourceList;
    }

    /**
     * 按id查询数据
     * @param id
     * @return
     */
    public Role findById(Long id) {
        return this.roleDao.findById(id).orElse(null);
    }

    /**
     * 按id删除数据
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        QRole qRole = QRole.role;
        QUserRole qUserRole = QUserRole.userRole;
        QRoleResource qRoleResource = QRoleResource.roleResource;

        this.factory
                .delete(qRole)
                .where(qRole.id.eq(id))
                .execute();
        this.factory
                .delete(qUserRole)
                .where(qUserRole.roleId.eq(id))
                .execute();
        this.factory
                .delete(qRoleResource)
                .where(qRoleResource.roleId.eq(id))
                .execute();
    }

    /**
     * 新增或修改数据
     * @param role
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Role role) {
        User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        if (null == role.getId()) {
            role.setCreateName(user.getRealName());
            role.setUpdateName(user.getRealName());
            this.roleDao.save(role);
        } else {
            Role oldRole = this.roleDao.findById(role.getId()).orElse(null);
            oldRole.setRoleName(role.getRoleName());
            oldRole.setRemark(role.getRemark());
            oldRole.setUpdateName(user.getRealName());
            this.roleDao.save(oldRole);
        }
    }

    /**
     * 保存角色权限关联
     * @param roleId
     * @param resIds
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateRoleResource(Long roleId, String resIds) {
        //删除旧数据
        QRoleResource qRoleResource = QRoleResource.roleResource;
        this.factory
                .delete(qRoleResource)
                .where(qRoleResource.roleId.eq(roleId))
                .execute();

        //保存新数据
        if (StringUtils.isNotEmpty(resIds)) {
            User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
            resIds = resIds.substring(resIds.indexOf("_") + 1, resIds.lastIndexOf("_"));
            String []resIdArray = resIds.split("_");
            List<RoleResource> roleResourceList = new ArrayList<>();
            for (String resId : resIdArray) {
                RoleResource roleResource = new RoleResource();
                roleResource.setRoleId(roleId);
                roleResource.setResId(Long.parseLong(resId));
                roleResource.setCreateName(user.getRealName());
                roleResource.setUpdateName(user.getRealName());
                roleResourceList.add(roleResource);
            }
            this.roleResourceDao.saveAll(roleResourceList);
        }
    }
}
