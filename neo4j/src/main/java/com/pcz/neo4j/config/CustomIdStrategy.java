package com.pcz.neo4j.config;

import cn.hutool.core.util.IdUtil;
import org.neo4j.ogm.id.IdStrategy;

/**
 * @author picongzhi
 */
public class CustomIdStrategy implements IdStrategy {
    @Override
    public Object generateId(Object o) {
        return IdUtil.fastUUID();
    }
}
