package com.ua.foxminded.task_11.services;

import com.ua.foxminded.task_11.dao.impl.LessonDaoImpl;
import com.ua.foxminded.task_11.exceptions.ServiceException;
import com.ua.foxminded.task_11.model.Lector;
import com.ua.foxminded.task_11.model.Lesson;
import com.ua.foxminded.task_11.validation.ValidatorEntity;
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
        List<Lesson> list = new ArrayList<>();
        Lesson lOne = new Lesson(1, "technology", new Lector());
        Lesson lTwo = new Lesson(1, "technology", new Lector());
        Lesson lThree = new Lesson(1, "technology", new Lector());

        list.add(lOne);
        list.add(lTwo);
        list.add(lThree);

        when(lessonDao.getAll()).thenReturn(list);

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
        lessonServices.delete(1L);
        verify(lessonDao, times(1)).delete(1L);
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
    public void shouldOutputAppropriateSentencesWhenNameIsNull() {
        Lesson lesson = new Lesson(1L, null, new Lector());

        assertThrows(ServiceException.class, () -> validator.validate(lesson));
    }

    @Test
    public void shouldOutputAppropriateSentencesWhenNameOrSurnameTooShort() {
        Lesson lesson = new Lesson(1L, "V", new Lector());

        assertThrows(ServiceException.class, () -> validator.validate(lesson));
    }

    @Test
    public void shouldPassWhenValid() {

        Lesson lesson = new Lesson(1L, "ValidName", new Lector());

        assertDoesNotThrow(() -> validator.validate(lesson));
        assertEquals("ValidName",lesson.getName());
    }

}