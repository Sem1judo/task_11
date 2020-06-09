package com.ua.foxminded.task_11.services;

import com.ua.foxminded.task_11.dao.impl.LessonDaoImpl;
import com.ua.foxminded.task_11.exceptions.DaoException;
import com.ua.foxminded.task_11.exceptions.ServicesException;

import com.ua.foxminded.task_11.model.Faculty;
import com.ua.foxminded.task_11.model.Lesson;
import com.ua.foxminded.task_11.validation.ValidatorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class LessonServices {

    @Autowired
    private LessonDaoImpl lessonDao;
    @Autowired
    private ValidatorEntity validator;

    public List<Lesson> getLessons() throws ServicesException {
        try {
            return lessonDao.getAll();
        } catch (DaoException e) {
            throw new ServicesException("Failed to get lessons", e);
        }
    }


    public boolean createNewLesson(@Valid Lesson lesson) throws ServicesException {
        try {
          return lessonDao.create(lesson);
        } catch (DaoException e) {
            throw new ServicesException("Failed to create lesson", e);
        }
    }

    public boolean deleteLesson(long id) throws ServicesException {
        try {
          return lessonDao.delete(id);
        } catch (DaoException e) {
            throw new ServicesException("Failed to delete lesson by id", e);
        }
    }


    public Lesson getLesson(long id) throws ServicesException {
        Lesson lesson;
        try {
            lesson = lessonDao.getById(id);
        } catch (DaoException e) {
            throw new ServicesException("Failed to retrieve lesson by id", e);
        }
        return lesson;
    }

    public boolean updateLesson(@Valid Lesson lesson) throws ServicesException {
        if (lesson.getLessonId() == 0) {
            throw new ServicesException("Missing id");
        }

        Set<ConstraintViolation<Lesson>> constraintViolations = validator.getValidatorInstance().validate(lesson);
        if (!constraintViolations.isEmpty()) {
            throw new ServicesException("Data is not valid: " + constraintViolations.iterator().next());
        }

        try {
            lessonDao.getById(lesson.getLessonId());
        } catch (DaoException e) {
            throw new ServicesException("Failed to retrieve lesson by id", e);
        }

        try {
            return lessonDao.update(lesson);
        } catch (DaoException e) {
            throw new ServicesException("Problem with updating lesson");
        }
    }
}
