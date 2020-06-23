package com.ua.foxminded.task_12.dao.impl;


import com.ua.foxminded.task_12.model.Lesson;
import com.ua.foxminded.task_12.model.mapper.LessonMapper;
import com.ua.foxminded.task_12.validation.ValidatorEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class LessonDaoImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;
    @InjectMocks
    private LessonDaoImpl lessonDao;
    @InjectMocks
    ValidatorEntity validator;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(lessonDao, "jdbcTemplate", jdbcTemplate);

    }

    @Test
    public void shouldCreateLesson() {
        when(jdbcTemplate.update(eq("insert into lessons(lesson_name) values(?)"), eq("testName")))
                .thenReturn(1);

        Lesson lesson = new Lesson();
        lesson.setName("testName");

        boolean isCreated = lessonDao.create(lesson);
        assertTrue(isCreated);
    }

    @Test
    public void shouldUpdatedLesson() {
        when(jdbcTemplate.update(eq("update lessons set lesson_name = ? where lesson_id = ?"),
                eq("testName"), eq(1L)))
                .thenReturn(1);

        Lesson lesson = new Lesson();
        lesson.setLessonId(1L);
        lesson.setName("testName");
        boolean isUpdated = lessonDao.update(lesson);

        assertTrue(isUpdated);
    }

    @Test
    public void shouldDeleteLesson() {
        when(jdbcTemplate.update(anyString(), eq(1L))).
                thenReturn(1);

        boolean isDeleted = lessonDao.delete(1L);

        assertTrue(isDeleted);
    }

    @Test
    public void shouldReturnAppropriateName() {
        when(jdbcTemplate.queryForObject(eq("select * from lessons where lesson_id = ?"), eq(new Object[]{100L}), (LessonMapper) any()))
                .thenReturn(getMeTestLesson());

        Lesson lesson = lessonDao.getById(100L);

        assertNotNull(lesson);
        assertEquals("testName", lesson.getName());
    }

    private Lesson getMeTestLesson() {
        Lesson lesson = new Lesson();
        lesson.setLessonId(100L);
        lesson.setName("testName");

        return lesson;
    }

}

