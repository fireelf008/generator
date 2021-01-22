package com.wsf.generator.modular.test.controller;

import com.wsf.generator.modular.test.entity.Test;
import com.wsf.generator.modular.test.service.TestService;
import com.wsf.gen.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/test/test")
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping(value = "/list")
    public String list() {
        return "mng/test/test_list";
    }

    @PostMapping("/findList")
    @ResponseBody
    public ResultVo findList(Integer page, Integer limit, Test test) {
        try {
            PageRequest pageRequest = PageRequest.of(page - 1, limit, Sort.Direction.DESC, "id");
            Page<Test> testPage = this.testService.findAll(pageRequest, test);
            return ResultVo.success(testPage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.error(e.getMessage());
        }
    }

    @GetMapping(value = "/find/{id}")
    @ResponseBody
    public ResultVo find(@PathVariable("id") Long id) {
        try {
            Test test = this.testService.findById(id);
            return ResultVo.success(test);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.error(e.getMessage());
        }
    }

    @GetMapping(value = "/toAdd")
    public String toAdd() {
        return "mng/test/test_add";
    }

    @PostMapping("/add")
    @ResponseBody
    public ResultVo add(Test test) {
        try {
            this.testService.saveOrUpdate(test);
            return ResultVo.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.error(e.getMessage());
        }
    }

    @GetMapping(value = "/toEdit/{id}")
    public String toEdit(Model model, @PathVariable("id") Long id) {
        Test test = this.testService.findById(id);
        model.addAttribute("test", test);
        return "mng/test/test_edit";
    }

    @PostMapping("/edit")
    @ResponseBody
    public ResultVo edit(Test test) {
        try {
            this.testService.saveOrUpdate(test);
            return ResultVo.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.error(e.getMessage());
        }
    }

    @GetMapping(value = "/del/{id}")
    @ResponseBody
    public ResultVo del(@PathVariable("id") Long id) {
        try {
            this.testService.deleteById(id);
            return ResultVo.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.error(e.getMessage());
        }
    }
}