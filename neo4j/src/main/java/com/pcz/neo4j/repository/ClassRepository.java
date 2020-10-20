package com.pcz.neo4j.repository;

import com.pcz.neo4j.model.Class;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

/**
 * @author picongzhi
 */
public interface ClassRepository extends Neo4jRepository<Class, String> {
    /**
     * 根据班级名称查询班级信息
     *
     * @param name 名称
     * @return 班级信息
     */
    Optional<Class> findByName(String name);
}
