package com.hh99.nearby.repository.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class QueryDslConfig {

    @PersistenceContext
    private EntityManager entityManager; // 엔티티를 관리하는 클래스

    @Bean
    public JPAQueryFactory jpaQueryFactory() { // JPAQueryFactory Bean 등록
        return new JPAQueryFactory(entityManager);
    }

}