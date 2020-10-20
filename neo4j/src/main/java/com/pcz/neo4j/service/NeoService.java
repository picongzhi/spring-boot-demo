package com.pcz.neo4j.service;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.pcz.neo4j.model.Class;
import com.pcz.neo4j.model.Lesson;
import com.pcz.neo4j.model.Student;
import com.pcz.neo4j.model.Teacher;
import com.pcz.neo4j.payload.ClassmateInfoGroupByLesson;
import com.pcz.neo4j.payload.TeacherStudent;
import com.pcz.neo4j.repository.ClassRepository;
import com.pcz.neo4j.repository.LessonRepository;
import com.pcz.neo4j.repository.StudentRepository;
import com.pcz.neo4j.repository.TeacherRepository;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.neo4j.ogm.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author picongzhi
 */
@Service
public class NeoService {
    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public void initData() {
        Teacher akai = Teacher.of("迈特凯");
        Teacher kakaxi = Teacher.of("卡卡西");
        Teacher zilaiye = Teacher.of("自来也");
        Teacher gangshou = Teacher.of("纲手");
        Teacher dashewan = Teacher.of("大蛇丸");
        teacherRepository.save(akai);
        teacherRepository.save(kakaxi);
        teacherRepository.save(zilaiye);
        teacherRepository.save(gangshou);
        teacherRepository.save(dashewan);

        Lesson tishu = Lesson.of("体术", akai);
        Lesson huanshu = Lesson.of("幻术", kakaxi);
        Lesson shoulijian = Lesson.of("手里剑", kakaxi);
        Lesson luoxuanwan = Lesson.of("螺旋丸", zilaiye);
        Lesson xianshu = Lesson.of("仙术", zilaiye);
        Lesson yiliao = Lesson.of("医疗", gangshou);
        Lesson zhouyin = Lesson.of("咒印", dashewan);
        lessonRepository.save(tishu);
        lessonRepository.save(huanshu);
        lessonRepository.save(shoulijian);
        lessonRepository.save(luoxuanwan);
        lessonRepository.save(xianshu);
        lessonRepository.save(yiliao);
        lessonRepository.save(zhouyin);

        Class three = Class.of("第三班", akai);
        Class seven = Class.of("第七班", kakaxi);
        classRepository.save(three);
        classRepository.save(seven);

        List<Student> threeClass = Lists.newArrayList(Student.of("漩涡鸣人", Lists.newArrayList(tishu, shoulijian, luoxuanwan, xianshu), seven), Student
                .of("宇智波佐助", Lists.newArrayList(huanshu, zhouyin, shoulijian), seven), Student.of("春野樱", Lists.newArrayList(tishu, yiliao, shoulijian), seven));
        List<Student> sevenClass = Lists.newArrayList(Student.of("李洛克", Lists.newArrayList(tishu), three), Student.of("日向宁次", Lists
                .newArrayList(tishu), three), Student.of("天天", Lists.newArrayList(tishu), three));
        studentRepository.saveAll(threeClass);
        studentRepository.saveAll(sevenClass);
    }

    @Transactional
    public void delete() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.query("match (n)-[r]-() delete n, r", Maps.newHashMap());
        session.query("match (n)-[r]-() delete r", Maps.newHashMap());
        session.query("match (n) delete n", Maps.newHashMap());
        transaction.commit();

        studentRepository.deleteAll();
        classRepository.deleteAll();
        lessonRepository.deleteAll();
        teacherRepository.deleteAll();
    }

    public List<Lesson> findLessonsFromStudent(String studentName, int depth) {
        List<Lesson> lessons = Lists.newArrayList();
        studentRepository.findByName(studentName, depth).ifPresent(student -> lessons.addAll(student.getLessons()));

        return lessons;
    }

    public Long studentCount(String className) {
        if (StrUtil.isBlank(className)) {
            return studentRepository.count();
        } else {
            return studentRepository.countByClassName(className);
        }
    }

    public Map<String, List<Student>> findClassmatesGroupByLesson() {
        List<ClassmateInfoGroupByLesson> groupByLesson = studentRepository.findByClassmateGroupByLesson();
        Map<String, List<Student>> result = Maps.newHashMap();

        groupByLesson.forEach(classmateInfoGroupByLesson -> result.put(classmateInfoGroupByLesson.getLessonName(),
                classmateInfoGroupByLesson.getStudents()));

        return result;
    }

    public Map<String, Set<Student>> findTeacherStudent() {
        List<TeacherStudent> teacherStudentByClass = studentRepository.findTeacherStudentByClass();
        List<TeacherStudent> teacherStudentByLesson = studentRepository.findTeacherStudentByLesson();
        Map<String, Set<Student>> result = Maps.newHashMap();

        teacherStudentByClass.forEach(teacherStudent -> result.put(teacherStudent.getTeacherName(), Sets.newHashSet(teacherStudent.getStudents())));
        teacherStudentByLesson.forEach(teacherStudent -> result.put(teacherStudent.getTeacherName(), Sets.newHashSet(teacherStudent.getStudents())));

        return result;
    }
}
