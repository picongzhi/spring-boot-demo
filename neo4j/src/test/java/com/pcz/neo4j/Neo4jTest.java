package com.pcz.neo4j;

import cn.hutool.json.JSONUtil;
import com.pcz.neo4j.model.Lesson;
import com.pcz.neo4j.model.Student;
import com.pcz.neo4j.service.NeoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class Neo4jTest extends SpringBootNeo4jApplicationTest {
    @Autowired
    private NeoService neoService;

    @Test
    public void testSave() {
        neoService.initData();
    }

    @Test
    public void testDelete() {
        neoService.delete();
    }

    @Test
    public void testFindLessonsByStudent() {
        List<Lesson> lessonList = neoService.findLessonsFromStudent("漩涡鸣人", 2);
        lessonList.forEach(lesson -> log.info("[lesson] = {}", JSONUtil.toJsonStr(lesson)));
    }

    @Test
    public void testCountStudent() {
        Long all = neoService.studentCount(null);
        log.info("[全校人数] = {}", all);
        Long seven = neoService.studentCount("第七班");
        log.info("[第七班人数] = {}", seven);
    }

    @Test
    public void testFindClassmates() {
        Map<String, List<Student>> classmates = neoService.findClassmatesGroupByLesson();
        classmates.forEach((k, v) -> log.info("{}: {}", k, JSONUtil.toJsonStr(v
                .stream()
                .map(Student::getName)
                .collect(Collectors.toList()))));
    }

    @Test
    public void testFindTeacherStudent() {
        Map<String, Set<Student>> teacherStudent = neoService.findTeacherStudent();
        teacherStudent.forEach((k, v) -> log.info("{}: {}", k, JSONUtil.toJsonStr(v
                .stream()
                .map(Student::getName)
                .collect(Collectors.toList()))));
    }
}
