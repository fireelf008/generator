package com.wsf.generator.modular.system.controller;

import com.wsf.generator.modular.system.entity.*;
import com.wsf.generator.modular.system.service.RoleService;
import com.wsf.generator.modular.system.service.UserService;
import com.wsf.gen.vo.ResultVo;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "/sys/user")
@Api
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping(value = "/login")
    public String login() {
        return "mng/login";
    }

    /**
     * 登录
     * @param username
     * @param pwd
     * @return
     */
    @PostMapping(value = "/loginVali")
    @ResponseBody
    public ResultVo loginVali(HttpSession session, String username, String pwd) {

        try {
            // 添加用户认证信息
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, pwd);
            SecurityUtils.getSubject().login(usernamePasswordToken);
            User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
            session.setAttribute("user", user);
            List<Resource> resourceList = this.userService.loadResource(user);
            session.setAttribute("menu", resourceList);

            //修改最后登录时间
            this.userService.updateLastTime(user.getId());
            return ResultVo.success(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.error(e.getMessage());
        }
    }

    /**
     * 登出
     * @return
     */
    @GetMapping(value = "/logout")
    @ResponseBody
    public ResultVo logout(HttpSession session) {
        try {
            SecurityUtils.getSubject().logout();
            session.invalidate();

            return ResultVo.success(null);
        } catch (Exception e) {
            if (e instanceof IllegalStateException) {
                return ResultVo.success(null);
            } else {
                e.printStackTrace();
                return ResultVo.error(e.getMessage());
            }
        }
    }

    /**
     * 跳转到后台首页
     * @return
     */
    @GetMapping(value = "/index")
    public String index() {
        return "mng/index";
    }

    /**
     * 跳转到列表页
     * @return
     */
    @GetMapping(value = "/list")
    @RequiresPermissions("user_list")
    public String list(Model model) {
        return "mng/system/user_list";
    }

    /**
     * 多条件分页查询
     * @param page
     * @param limit
     * @param user
     * @return
     */
    @PostMapping(value = "/findList")
    @RequiresPermissions("user_list")
    @ResponseBody
    public ResultVo findList(Integer page, Integer limit, User user) {
        try {
            PageRequest pageRequest = PageRequest.of(page - 1, limit, Sort.Direction.DESC, "id");
            Page<User> userPage = this.userService.findAll(pageRequest, user);
            return ResultVo.success(userPage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.error(e.getMessage());
        }
    }

    /**
     * 跳转到新增页
     * @return
     */
    @GetMapping(value = "/toAdd")
    @RequiresPermissions("user_add")
    public String toAdd(Model model) {
        List<Role> roleList = this.roleService.findAll();
        model.addAttribute("roleList", roleList);
        return "mng/system/user_add";
    }

    /**
     * 新增
     * @return
     */
    @PostMapping(value = "/add")
    @RequiresPermissions("user_add")
    @ResponseBody
    public ResultVo add(User user) {
        try {
            this.userService.saveOrUpdate(user);
            return ResultVo.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.error(e.getMessage());
        }
    }

    /**
     * 跳转到编辑页
     * @return
     */
    @GetMapping(value = "/toEdit/{id}")
    @RequiresPermissions("user_edit")
    public String toEdit(Model model, @PathVariable("id") Long id) {
        User user = this.userService.findById(id);
        List<Role> roleList = this.roleService.findAll();
        model.addAttribute("user", user);
        model.addAttribute("roleList", roleList);
        return "mng/system/user_edit";
    }

    /**
     * 编辑
     * @return
     */
    @PostMapping(value = "/edit")
    @RequiresPermissions("user_edit")
    @ResponseBody
    public ResultVo edit(User user) {
        try {
            this.userService.saveOrUpdate(user);
            return ResultVo.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.error(e.getMessage());
        }
    }

    /**
     * 删除
     * @return
     */
    @GetMapping(value = "/del/{id}")
    @RequiresPermissions("user_del")
    @ResponseBody
    public ResultVo del(@PathVariable("id") Long id) {
        try {
            this.userService.deleteById(id);
            return ResultVo.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.error(e.getMessage());
        }
    }

    /**
     * 冻结/解冻
     * @param id
     * @param enable
     * @return
     */
    @PostMapping(value = "/freeze")
    @RequiresPermissions("user_freeze")
    @ResponseBody
    public ResultVo freeze(Long id, Integer enable) {
        try {
            this.userService.freeze(id, enable);
            return ResultVo.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.error(e.getMessage());
        }
    }

    /**
     * 跳转到后台首页
     * @return
     */
    @GetMapping(value = "/toUpdatePwd")
    public String toUpdatePwd() {
        return "mng/system/update_pwd";
    }

    /**
     * 修改密码
     * @param id
     * @param oldPwd
     * @param newPwd
     * @return
     */
    @PostMapping(value = "/updatePwd")
    @ResponseBody
    public ResultVo updatePwd(Long id, String oldPwd, String newPwd) {
        try {
            this.userService.updatePwd(id, oldPwd, newPwd);
            return ResultVo.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.error(e.getMessage());
        }
    }
}
