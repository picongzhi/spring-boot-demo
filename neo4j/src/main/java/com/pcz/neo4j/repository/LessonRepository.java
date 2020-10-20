package com.pcz.neo4j.repository;

import com.pcz.neo4j.model.Lesson;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * @author picongzhi
 */
public interface LessonRepository extends Neo4jRepository<Lesson, String> {
}
