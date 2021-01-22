package com.wsf.generator.modular.system.controller;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.wsf.generator.modular.system.entity.QResource;
import com.wsf.generator.modular.system.entity.Resource;
import com.wsf.generator.modular.system.service.ResourceService;
import com.wsf.gen.vo.ResultVo;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/sys/res")
@Api
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    /**
     * 跳转到列表页
     * @return
     */
    @GetMapping(value = "/list")
    @RequiresPermissions("res_list")
    public String list() {
        return "mng/system/res_list";
    }

    /**
     * 多条件分页查询
     * @param page
     * @param limit
     * @param resource
     * @return
     */
    @PostMapping(value = "/findList")
    @RequiresPermissions("res_list")
    @ResponseBody
    public ResultVo findList(Integer page, Integer limit, Resource resource) {
        try {
            PageRequest pageRequest = PageRequest.of(page - 1, limit, Sort.Direction.DESC, "id");
            Page<Resource> resourcePage = this.resourceService.findAll(pageRequest, resource);
            return ResultVo.success(resourcePage);
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
    @RequiresPermissions("res_add")
    public String toAdd() {
        return "mng/system/res_add";
    }

    /**
     * 新增
     * @return
     */
    @PostMapping(value = "/add")
    @RequiresPermissions("res_add")
    @ResponseBody
    public ResultVo add(Resource resource) {
        try {
            this.resourceService.saveOrUpdate(resource);
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
    @RequiresPermissions("res_edit")
    public String toEdit(Model model, @PathVariable("id") Long id) {
        Resource resource = this.resourceService.findById(id);
        model.addAttribute("res", resource);
        return "mng/system/res_edit";
    }

    /**
     * 编辑
     * @return
     */
    @PostMapping(value = "/edit")
    @RequiresPermissions("res_edit")
    @ResponseBody
    public ResultVo edit(Resource resource) {
        try {
            this.resourceService.saveOrUpdate(resource);
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
    @RequiresPermissions("res_del")
    @ResponseBody
    public ResultVo del(@PathVariable("id") Long id) {
        try {
            this.resourceService.deleteById(id);
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
            List<Resource> resourceList = this.resourceService.findAll();

            Resource rootResource = new Resource();
            rootResource.setId(0L);
            rootResource.setResName("全部");
            rootResource.setResCode("root");
            resourceList.add(rootResource);

            return ResultVo.success(resourceList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.error(e.getMessage());
        }
    }

    @GetMapping(value = "/toTree")
    public String toTree(Model model, Long id) {
        model.addAttribute("id", id);
        return "mng/system/res_tree";
    }
}
