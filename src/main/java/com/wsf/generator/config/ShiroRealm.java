package com.wsf.generator.config;

import com.alibaba.fastjson.JSON;
import com.wsf.generator.modular.system.dao.UserDao;
import com.wsf.generator.modular.system.entity.Resource;
import com.wsf.generator.modular.system.entity.Role;
import com.wsf.generator.modular.system.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserDao userDao;

    /**
     * 认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //按用户名查询用户对象
        User user = this.userDao.findByUsername(token.getPrincipal().toString());
        if (user == null) {
            throw new AuthenticationException("用户名或密码错误");
        }
        if (0 == user.getEnable()) {
            throw new AuthenticationException("用户已冻结");
        }
        log.info("用户{}登陆：{}", user.getUsername(), JSON.toJSONString(user));

        //获取盐值对密码加盐后与数据库对比
        ByteSource salt = ByteSource.Util.bytes(user.getSalt());
        String saltPwd = new SimpleHash("MD5", token.getCredentials(), salt).toString();
        if (!user.getPassword().equals(saltPwd)) {
            throw new AuthenticationException("用户名或密码错误");
        }

        //这里验证authenticationToken和simpleAuthenticationInfo的信息
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, token.getCredentials(), salt, getName());
        return simpleAuthenticationInfo;
    }

    /**
     * 授权
     * @param collection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection collection) {

        //获取登录用户
        User user = (User) collection.getPrimaryPrincipal();

        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (Role role : user.getRoleList()) {

            //添加角色
            simpleAuthorizationInfo.addRole(role.getRoleCode());
            for (Resource resource : role.getResourceList()) {

                //添加权限
                simpleAuthorizationInfo.addStringPermission(resource.getResCode());
            }
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 实现超管授权
     * @param principals
     * @param permission
     * @return
     */
    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        User user = (User) principals.getPrimaryPrincipal();
        if (null != user) {
            return 1 == user.getAdmin() || super.isPermitted(principals, permission);
        }
        return super.isPermitted(principals, permission);
    }

    /**
     * 实现超管授权
     * @param principals
     * @param roleIdentifier
     * @return
     */
    @Override
    public boolean hasRole(PrincipalCollection principals, String roleIdentifier) {
        User user = (User) principals.getPrimaryPrincipal();
        if (null != user) {
            return 1 == user.getAdmin() || super.hasRole(principals, roleIdentifier);
        }
        return super.hasRole(principals, roleIdentifier);
    }
}
