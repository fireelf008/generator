package ${config.daoPackage};

import ${config.entityPackage}.${config.entityName};
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ${config.daoName} extends JpaRepository<${config.entityName}, ${config.idType}>, QuerydslPredicateExecutor<${config.entityName}> {

}