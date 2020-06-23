package com.ua.foxminded.task_12.services;

import com.ua.foxminded.task_12.dao.impl.LessonDaoImpl;
import com.ua.foxminded.task_12.exceptions.ServiceException;
import com.ua.foxminded.task_12.model.Lector;
import com.ua.foxminded.task_12.model.Lesson;
import com.ua.foxminded.task_12.validation.ValidatorEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class LessonServicesTest {

    @Mock
    private LessonDaoImpl lessonDao;

    @InjectMocks
    private ValidatorEntity<Lesson> validator;

    @InjectMocks
    private LessonServices lessonServices;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(lessonServices, "validator", validator);
    }

    @Test
    public void shouldGetAllLessons() {
        List<Lesson> initialLessons = new ArrayList<>();
        Lesson testLesson1 = new Lesson(1, "technology", new Lector());
        Lesson testLesson2 = new Lesson(1, "technology", new Lector());
        Lesson testLesson3 = new Lesson(1, "technology", new Lector());

        initialLessons.add(testLesson1);
        initialLessons.add(testLesson2);
        initialLessons.add(testLesson3);

        when(lessonDao.getAll()).thenReturn(initialLessons);

        List<Lesson> lessons = lessonServices.getAll();

        assertEquals(3, lessons.size());
        verify(lessonDao, times(1)).getAll();
    }

    @Test
    public void shouldGetByIdLesson() {
        when(lessonDao.getById(1L)).thenReturn(new Lesson(1, "technology", new Lector()));

        Lesson lesson = lessonServices.getById(1L);

        assertEquals("technology", lesson.getName());
        assertEquals(1, lesson.getLessonId());
    }

    @Test
    public void shouldCreateLesson() {
        Lesson lesson = new Lesson(1, "technology", new Lector());

        when(lessonDao.create(eq(lesson))).thenReturn(Boolean.TRUE);

        boolean isCreated = lessonServices.create(lesson);

        verify(lessonDao, times(1)).create(lesson);
        assertTrue(isCreated);
    }

    @Test
    public void shouldDeleteLesson() {

        when(lessonDao.delete(1L)).thenReturn(Boolean.TRUE);
        boolean isDeleted = lessonServices.delete(1L);

        verify(lessonDao, times(1)).delete(1L);
        assertTrue(isDeleted);
    }

    @Test
    public void shouldUpdateLesson() {
        Lesson lesson = new Lesson(1, "technology", new Lector());
        when(lessonDao.update(eq(lesson))).thenReturn(Boolean.TRUE);

        boolean isUpdated = lessonServices.update(lesson);
        verify(lessonDao, times(1)).update(lesson);
        assertTrue(isUpdated);
    }

    @Test
    public void shouldThrowServiceExceptionWhenNameIsNull() {
        Lesson lesson = new Lesson(1L, null, new Lector());
        assertThrows(ServiceException.class, () -> lessonServices.create(lesson));
    }

    @Test
    public void shouldThrowServiceExceptionWhenNameTooShort() {
        Lesson lesson = new Lesson(1L, "V", new Lector());
        assertThrows(ServiceException.class, () -> lessonServices.create(lesson));
    }

    @Test
    public void shouldThrowServiceExceptionWhenIdZero() {
        Lesson lesson = new Lesson(0, "Vansss", new Lector());
        assertThrows(ServiceException.class, () -> lessonServices.update(lesson));
    }

    @Test
    public void shouldThrowServiceExceptionWhenNameHaveForbiddenSymbol() {
        Lesson lesson = new Lesson(0L, "Va!nss^s", new Lector());
        assertThrows(ServiceException.class, () -> lessonServices.create(lesson));
    }

    @Test
    public void shouldThrowServiceExceptionWhenNameIsTooLong() {
        Lesson lesson = new Lesson(0L, "Vaaaaaaaaaaansssssssssssssssssssssssssssssssssssssssssss", new Lector());
        assertThrows(ServiceException.class, () -> lessonServices.create(lesson));
    }


}