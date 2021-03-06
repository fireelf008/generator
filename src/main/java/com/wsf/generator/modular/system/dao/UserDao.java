package com.wsf.generator.modular.system.dao;

import com.wsf.generator.modular.system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {

    User findByUsername(String username);
}
