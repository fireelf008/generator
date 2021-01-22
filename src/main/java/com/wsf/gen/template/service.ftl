package ${config.servicePackage};

import ${config.daoPackage}.${config.daoName};
import ${config.entityPackage}.${config.qentityName};
import ${config.entityPackage}.${config.entityName};
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ${config.serviceName} {

    @Autowired
    private JPAQueryFactory factory;

    @Autowired
    private ${config.daoName} ${config.varDaoName};

    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(${config.entityName} ${config.varEntityName}) {
        this.${config.varDaoName}.save(${config.varEntityName});
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(List<${config.entityName}> ${config.varEntityName}List) {
        this.${config.varDaoName}.saveAll(${config.varEntityName}List);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(${config.idType} ${config.idName}) {
        this.${config.varDaoName}.deleteById(${config.idName});
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(List<${config.idType}> ${config.idName}List) {
        ${config.qentityName} ${config.qvarEntityName} = ${config.qentityName}.${config.varEntityName};
        this.factory
                .delete(${config.qvarEntityName})
                .where(${config.qvarEntityName}.${config.idName}.in(${config.idName}List));
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(${config.entityName} ${config.varEntityName}) {
        this.deleteById(${config.varEntityName}.${config.idMethodName}());
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(List<${config.entityName}> ${config.varEntityName}List) {
        List<${config.idType}> ${config.idName}List = ${config.varEntityName}List.stream().map(${config.entityName}::${config.idMethodName}).collect(Collectors.toList());
        this.deleteById(${config.idName}List);
    }

    public ${config.entityName} findById(${config.idType} ${config.idName}) {
        return this.${config.varDaoName}.findById(${config.idName}).orElse(null);
    }

    public List<${config.entityName}> findAll() {
        return this.${config.varDaoName}.findAll();
    }

    public List<${config.entityName}> findAll(Sort sort) {
        return this.${config.varDaoName}.findAll(sort);
    }

    public Page<${config.entityName}> findAll(PageRequest pageRequest) {
        return this.${config.varDaoName}.findAll(pageRequest);
    }

    public Page<${config.entityName}> findAll(PageRequest pageRequest, ${config.entityName} ${config.varEntityName}) {
        ${config.qentityName} ${config.qvarEntityName} = ${config.qentityName}.${config.varEntityName};

        Predicate where = ${config.qvarEntityName}.${config.idName}.isNotNull();
        <#list config.fieldColumnMap?keys as key>
        if (ObjectUtils.isNotEmpty(${config.varEntityName}.${config.fieldColumnMap[key].methodName}())) {
            <#if config.fieldColumnMap[key].type != "String">
            where = ExpressionUtils.and(where, ${config.qvarEntityName}.${key}.eq(${config.varEntityName}.${config.fieldColumnMap[key].methodName}()));
            <#else>
            where = ExpressionUtils.and(where, ${config.qvarEntityName}.${key}.like("%" + ${config.varEntityName}.${config.fieldColumnMap[key].methodName}() +"%"));
            </#if>
        }
        </#list>
        return this.${config.varDaoName}.findAll(where, pageRequest);
    }
}