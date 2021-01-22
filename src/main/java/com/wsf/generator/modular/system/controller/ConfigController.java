package com.wsf.generator.modular.system.controller;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.wsf.generator.modular.system.entity.Config;
import com.wsf.generator.modular.system.entity.QConfig;
import com.wsf.generator.modular.system.service.ConfigService;
import com.wsf.gen.vo.ResultVo;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/sys/config")
@Api
public class ConfigController {

    @Autowired
    private ConfigService configService;

    /**
     * 跳转到列表页
     * @return
     */
    @GetMapping(value = "/list")
    @RequiresPermissions("config_list")
    public String list() {
        return "mng/system/config_list";
    }

    /**
     * 跳转到新增页
     * @return
     */
    @GetMapping(value = "/toAdd")
    @RequiresPermissions("config_add")
    public String toAdd() {
        return "mng/system/config_add";
    }

    /**
     * 跳转到编辑页
     * @return
     */
    @GetMapping(value = "/toEdit/{id}")
    @RequiresPermissions("config_edit")
    public String toEdit(Model model, @PathVariable("id") Long id) {
        Config config = this.configService.findById(id);
        model.addAttribute("config", config);
        return "mng/system/config_edit";
    }

    @PostMapping(value = "/findList")
    @RequiresPermissions("config_list")
    @ResponseBody
    public ResultVo findList(Integer page, Integer limit, String configName, String createTime) {
        try {
            QPageRequest pageRequest = QPageRequest.of(page - 1, limit, new OrderSpecifier(Order.DESC, QConfig.config.id));
            Page<Config> resourcePage = this.configService.findAll(pageRequest, configName, createTime);
            return ResultVo.success(resourcePage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.error(e.getMessage());
        }
    }

    /**
     * 新增
     * @return
     */
    @PostMapping(value = "/add")
    @RequiresPermissions("config_add")
    @ResponseBody
    public ResultVo add(Config config) {
        try {
            this.configService.saveOrUpdate(config);
            return ResultVo.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.error(e.getMessage());
        }
    }

    /**
     * 编辑
     * @return
     */
    @PostMapping(value = "/edit")
    @RequiresPermissions("config_edit")
    @ResponseBody
    public ResultVo edit(Config config) {
        try {
            this.configService.saveOrUpdate(config);
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
    @RequiresPermissions("config_del")
    @ResponseBody
    public ResultVo del(@PathVariable("id") Long id) {
        try {
            this.configService.deleteById(id);
            return ResultVo.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.error(e.getMessage());
        }
    }
}
