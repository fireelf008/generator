package com.wsf.generator.modular.system.dao;

import com.wsf.generator.modular.system.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<Role, Long>, QuerydslPredicateExecutor<Role> {

}
