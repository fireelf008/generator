package com.wsf.generator.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class QueryDslConfig {

    @Bean
    public JPAQueryFactory getJPAQueryFactory(@Autowired EntityManager em) {
        return new JPAQueryFactory(em);
    }
}
