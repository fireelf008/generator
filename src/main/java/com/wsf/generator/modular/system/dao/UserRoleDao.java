package com.wsf.generator.modular.system.dao;

import com.wsf.generator.modular.system.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleDao extends JpaRepository<UserRole, Long>, QuerydslPredicateExecutor<UserRole> {

    List<UserRole> findByUserId(Long id);
    List<UserRole> findByRoleId(Long id);
    void deleteByUserId(Long id);
}
