package ${config.controllerPackage};

import ${config.entityPackage}.${config.entityName};
import ${config.servicePackage}.${config.serviceName};
import com.wsf.gen.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/${config.modularName}/${config.varEntityName}")
public class ${config.controllerName} {

    @Autowired
    private ${config.serviceName} ${config.varServiceName};

    @GetMapping(value = "/list")
    public String list() {
        return "mng/${config.modularName}/${config.varEntityName}_list";
    }

    @PostMapping("/findList")
    @ResponseBody
    public ResultVo findList(Integer page, Integer limit, ${config.entityName} ${config.varEntityName}) {
        try {
            PageRequest pageRequest = PageRequest.of(page - 1, limit, Sort.Direction.DESC, "${config.idName}");
            Page<${config.entityName}> ${config.varEntityName}Page = this.${config.varServiceName}.findAll(pageRequest, ${config.varEntityName});
            return ResultVo.success(${config.varEntityName}Page);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.error(e.getMessage());
        }
    }

    @GetMapping(value = "/find/{${config.idName}}")
    @ResponseBody
    public ResultVo find(@PathVariable("${config.idName}") ${config.idType} ${config.idName}) {
        try {
            ${config.entityName} ${config.varEntityName} = this.${config.varServiceName}.findById(${config.idName});
            return ResultVo.success(${config.varEntityName});
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.error(e.getMessage());
        }
    }

    @GetMapping(value = "/toAdd")
    public String toAdd() {
        return "mng/${config.modularName}/${config.varEntityName}_add";
    }

    @PostMapping("/add")
    @ResponseBody
    public ResultVo add(${config.entityName} ${config.varEntityName}) {
        try {
            this.${config.varServiceName}.saveOrUpdate(${config.varEntityName});
            return ResultVo.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.error(e.getMessage());
        }
    }

    @GetMapping(value = "/toEdit/{${config.idName}}")
    public String toEdit(Model model, @PathVariable("${config.idName}") ${config.idType} ${config.idName}) {
        ${config.entityName} ${config.varEntityName} = this.${config.varServiceName}.findById(${config.idName});
        model.addAttribute("${config.varEntityName}", ${config.varEntityName});
        return "mng/${config.modularName}/${config.varEntityName}_edit";
    }

    @PostMapping("/edit")
    @ResponseBody
    public ResultVo edit(${config.entityName} ${config.varEntityName}) {
        try {
            this.${config.varServiceName}.saveOrUpdate(${config.varEntityName});
            return ResultVo.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.error(e.getMessage());
        }
    }

    @GetMapping(value = "/del/{${config.idName}}")
    @ResponseBody
    public ResultVo del(@PathVariable("${config.idName}") ${config.idType} ${config.idName}) {
        try {
            this.${config.varServiceName}.deleteById(${config.idName});
            return ResultVo.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.error(e.getMessage());
        }
    }
}