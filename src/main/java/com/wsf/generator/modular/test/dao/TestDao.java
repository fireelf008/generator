package com.wsf.generator.modular.test.dao;

import com.wsf.generator.modular.test.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TestDao extends JpaRepository<Test, Long>, QuerydslPredicateExecutor<Test> {

}