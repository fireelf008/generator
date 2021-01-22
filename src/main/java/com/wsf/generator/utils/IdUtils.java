package com.wsf.generator.utils;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.data.mapping.MappingException;

import java.io.Serializable;

/**
 * jpa主键生成策略
 * @author wsf
 */
public class IdUtils implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws MappingException {
        return IdWorker.getId();
    }
}
