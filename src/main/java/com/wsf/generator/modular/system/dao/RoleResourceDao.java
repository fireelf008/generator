package com.wsf.generator.modular.system.dao;

import com.wsf.generator.modular.system.entity.RoleResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleResourceDao extends JpaRepository<RoleResource, Long>, QuerydslPredicateExecutor<RoleResource> {

    List<RoleResource> findByResId(Long id);
    List<RoleResource> findByRoleId(Long id);
    void deleteByResId(Long id);
}
