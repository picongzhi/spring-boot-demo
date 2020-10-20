package com.pcz.neo4j.repository;

import com.pcz.neo4j.model.Teacher;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * @author picongzhi
 */
public interface TeacherRepository extends Neo4jRepository<Teacher, String> {
}
