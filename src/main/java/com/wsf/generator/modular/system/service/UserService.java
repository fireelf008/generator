package com.wsf.generator.modular.system.service;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wsf.generator.modular.system.dao.ResourceDao;
import com.wsf.generator.modular.system.dao.UserDao;
import com.wsf.generator.modular.system.dao.UserRoleDao;
import com.wsf.generator.modular.system.entity.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private JPAQueryFactory factory;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ResourceDao resourceDao;

    @Autowired
    private UserRoleDao userRoleDao;

    public List<User> findAll() {
        return this.userDao.findAll();
    }

    /**
     * 查询所有资源，并按父子关系封装
     * @param user
     * @return
     */
    public List<Resource> loadResource(User user) {

        List<Resource> resourceList = null;

        //判断是否是超管，超管查询所有权限
        if (1 == user.getAdmin()) {
            resourceList = this.resourceDao.findAll();
        } else {
            List<Role> roleList = user.getRoleList();
            resourceList = new ArrayList<>();
            for (Role role : roleList) {
                resourceList.addAll(role.getResourceList());
            }
        }

        List<Resource> rootResourceList = this.group(resourceList);
        return rootResourceList;
    }

    public List<Resource> group(List<Resource> resourceList) {

        //去重并过滤按钮权限，然后按sort字段排序
        resourceList = resourceList.stream().distinct().filter(res -> 0 == res.getType()).sorted(Comparator.comparing(res -> res.getSort())).collect(Collectors.toList());

        //排除顶层菜单，按父id分组
        Map<Long, List<Resource>> groupResourceMap = resourceList.stream().filter(res -> null != res.getPid() && 0 != res.getPid()).collect(Collectors.groupingBy(res -> res.getPid()));

        for (Map.Entry<Long, List<Resource>> entry : groupResourceMap.entrySet()) {
            for (Resource resource : resourceList) {
                if (entry.getKey().equals(resource.getId())) {
                    resource.setChildResourceList(entry.getValue());
                    continue;
                }
            }
        }

        //获取顶层菜单
        List<Resource> rootResourceList = resourceList.stream().filter(res -> null == res.getPid() || 0 == res.getPid()).collect(Collectors.toList());
        return rootResourceList;
    }

    /**
     * 分页查询用户列表
     * @param pageRequest
     * @param user
     * @return
     */
    public Page<User> findAll(PageRequest pageRequest, User user) {
        QUser qUser = QUser.user;

        Predicate where = qUser.id.isNotNull();
        if (ObjectUtils.isNotEmpty(user.getUsername())) {
            where = ExpressionUtils.and(where, qUser.username.like("%" + user.getUsername() + "%"));
        }
        if (ObjectUtils.isNotEmpty(user.getRealName())) {
            where = ExpressionUtils.and(where, qUser.realName.like("%" + user.getRealName() + "%"));
        }
        if (ObjectUtils.isNotEmpty(user.getSex())) {
            where = ExpressionUtils.and(where, qUser.sex.eq(user.getSex()));
        }
        if (ObjectUtils.isNotEmpty(user.getPhone())) {
            where = ExpressionUtils.and(where, qUser.phone.like("%" + user.getPhone() + "%"));
        }
        return this.userDao.findAll(where, pageRequest);
    }

    /**
     * 按id查询数据
     * @param id
     * @return
     */
    public User findById(Long id) {
        return this.userDao.findById(id).orElse(null);
    }

    /**
     * 按id删除数据
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        QUser qUser = QUser.user;
        this.factory
                .delete(qUser)
                .where(qUser.id.eq(id))
                .execute();
    }

    /**
     * 新增或修改数据
     * @param user
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(User user) throws Exception {
        User shiroUser = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        QUser qUser = QUser.user;
        QUserRole qUserRole = QUserRole.userRole;
        if (null == user.getId()) {
            List<User> userList = this.factory
                    .selectFrom(qUser)
                    .where(qUser.username.eq(user.getUsername()))
                    .fetch();
            if (ObjectUtils.isEmpty(userList)) {

                //密码加密
                String salt = UUID.randomUUID().toString().replaceAll("-", "");
                ByteSource saltByteSource = ByteSource.Util.bytes(salt);
                String saltPwd = new SimpleHash("MD5", user.getPassword(), saltByteSource).toString();

                user.setPassword(saltPwd);
                user.setSalt(salt);
                user.setRegTime(new Date());
                user.setCreateName(shiroUser.getRealName());
                user.setUpdateName(shiroUser.getRealName());
                this.userDao.save(user);

                UserRole userRole = new UserRole();
                userRole.setUserId(user.getId());
                userRole.setRoleId(user.getRoleId());
                userRole.setCreateName(shiroUser.getRealName());
                userRole.setUpdateName(shiroUser.getRealName());

                this.userRoleDao.save(userRole);
            } else {
                throw new Exception("用户名已存在");
            }
        } else {
            User oldUser = this.userDao.findById(user.getId()).orElse(null);

            oldUser.setRealName(user.getRealName());
            oldUser.setBirthday(user.getBirthday());
            oldUser.setPhone(user.getPhone());
            oldUser.setSex(user.getSex());
            oldUser.setUpdateName(shiroUser.getRealName());
            this.userDao.save(oldUser);

            this.factory
                    .delete(qUserRole)
                    .where(qUserRole.userId.eq(user.getId()))
                    .execute();

            UserRole userRole = new UserRole();
            userRole.setUserId(user.getId());
            userRole.setRoleId(user.getRoleId());
            userRole.setCreateName(shiroUser.getRealName());
            userRole.setUpdateName(shiroUser.getRealName());
            this.userRoleDao.save(userRole);
        }
    }

    /**
     * 修改最后登录时间
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateLastTime(Long id) {
        QUser qUser = QUser.user;
        this.factory
                .update(qUser)
                .set(qUser.lastTime, new Date())
                .where(qUser.id.eq(id))
                .execute();
    }

    /**
     * 冻结/解冻
     * @param id
     * @param enable
     */
    @Transactional(rollbackFor = Exception.class)
    public void freeze(Long id, Integer enable) {
        QUser qUser = QUser.user;
        this.factory
                .update(qUser)
                .set(qUser.enable, enable)
                .where(qUser.id.eq(id))
                .execute();
    }

    /**
     * 修改密码
     * @param id
     * @param oldPwd
     * @param newPwd
     */
    @Transactional(rollbackFor = Exception.class)
    public void updatePwd(Long id, String oldPwd, String newPwd) throws Exception {
        QUser qUser = QUser.user;
        User user = this.factory
                .selectFrom(qUser)
                .where(qUser.id.eq(id))
                .fetchOne();
        if (null != user) {
            //密码加密
            ByteSource saltByteSource = ByteSource.Util.bytes(user.getSalt());
            String saltOldPwd = new SimpleHash("MD5", oldPwd, saltByteSource).toString();
            if (saltOldPwd.equals(user.getPassword())) {
                String saltNewPwd = new SimpleHash("MD5", newPwd, saltByteSource).toString();
                this.factory
                        .update(qUser)
                        .set(qUser.password, saltNewPwd)
                        .where(qUser.id.eq(id))
                        .execute();
            } else {
                throw new Exception("旧密码错误");
            }
        }
    }
}
