package com.wsf.generator.modular.system.controller;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.wsf.generator.modular.system.entity.QRole;
import com.wsf.generator.modular.system.entity.Resource;
import com.wsf.generator.modular.system.entity.Role;
import com.wsf.generator.modular.system.service.ResourceService;
import com.wsf.generator.modular.system.service.RoleService;
import com.wsf.gen.vo.ResultVo;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping(value = "/sys/role")
@Api
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourceService resourceService;

    /**
     * 跳转到列表页
     * @return
     */
    @GetMapping(value = "/list")
    @RequiresPermissions("role_list")
    public String list() {
        return "mng/system/role_list";
    }

    /**
     * 多条件分页查询
     * @param page
     * @param limit
     * @param role
     * @return
     */
    @PostMapping(value = "/findList")
    @RequiresPermissions("role_list")
    @ResponseBody
    public ResultVo findList(Integer page, Integer limit, Role role) {
        try {
            PageRequest pageRequest = PageRequest.of(page - 1, limit, Sort.Direction.DESC, "id");
            Page<Role> rolePage = this.roleService.findAll(pageRequest, role);
            return ResultVo.success(rolePage);
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
    @RequiresPermissions("role_add")
    public String toAdd() {
        return "mng/system/role_add";
    }

    /**
     * 新增
     * @return
     */
    @PostMapping(value = "/add")
    @RequiresPermissions("role_add")
    @ResponseBody
    public ResultVo add(Role role) {
        try {
            this.roleService.saveOrUpdate(role);
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
    @RequiresPermissions("role_edit")
    public String toEdit(Model model, @PathVariable("id") Long id) {
        Role role = this.roleService.findById(id);
        model.addAttribute("role", role);
        return "mng/system/role_edit";
    }

    /**
     * 编辑
     * @return
     */
    @PostMapping(value = "/edit")
    @RequiresPermissions("role_edit")
    @ResponseBody
    public ResultVo edit(Role role) {
        try {
            this.roleService.saveOrUpdate(role);
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
    @RequiresPermissions("role_del")
    @ResponseBody
    public ResultVo del(@PathVariable("id") Long id) {
        try {
            this.roleService.deleteById(id);
            return ResultVo.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.error(e.getMessage());
        }
    }

    /**
     * 查询所有数据
     * @return
     */
    @GetMapping(value = "/findAll")
    @ResponseBody
    public ResultVo findAll(Long id) {
        try {
            List<Resource> resourceList = this.roleService.findAllResAndRoleRes(id);
            return ResultVo.success(resourceList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.error(e.getMessage());
        }
    }

    /**
     * 跳转到分配权限页
     * @return
     */
    @GetMapping(value = "/toRoleRes/{id}")
    @RequiresPermissions("role_res")
    public String toRoleRes(Model model, @PathVariable("id") Long id) {
        model.addAttribute("id", id);
        return "mng/system/role_res";
    }

    /**
     * 分配权限
     * @param roleId
     * @param resIds
     * @return
     */
    @PostMapping(value = "/res")
    @RequiresPermissions("role_res")
    @ResponseBody
    public ResultVo roleRes(Long roleId, String resIds) {
        try {
            this.roleService.saveOrUpdateRoleResource(roleId, resIds);
            return ResultVo.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.error(e.getMessage());
        }
    }
}