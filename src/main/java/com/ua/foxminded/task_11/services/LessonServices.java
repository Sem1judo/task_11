package com.ua.foxminded.task_11.services;

import com.ua.foxminded.task_11.dao.impl.LessonDaoImpl;
import com.ua.foxminded.task_11.exceptions.ServicesException;
import com.ua.foxminded.task_11.model.Lesson;
import com.ua.foxminded.task_11.validation.ValidatorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.validation.*;
import java.util.List;
import java.util.Set;

@Service
public class LessonServices {

    @Autowired
    private LessonDaoImpl lessonDao;
    @Autowired
    private ValidatorEntity validator;

    public List<Lesson> getLessons()  {
        try {
            return lessonDao.getAll();
        } catch (DataAccessException e) {
            throw new ServicesException("Failed to get list of lessons", e);
        }
    }


    public boolean createNewLesson(@Valid Lesson lesson)  {
        try {
          return lessonDao.create(lesson);
        } catch (DataAccessException e) {
            throw new ServicesException("Failed to create lesson", e);
        }
    }

    public boolean deleteLesson(long id)  {
        if (id == 0) {
            throw new ServicesException("Missing id");
        }
        try {
          return lessonDao.delete(id);
        } catch (DataAccessException e) {
            throw new ServicesException("Failed to delete lesson by id", e);
        }
    }


    public Lesson getLesson(long id)  {
        if (id == 0) {
            throw new ServicesException("Missing id");
        }
        Lesson lesson;
        try {
            lesson = lessonDao.getById(id);
        } catch (DataAccessException e) {
            throw new ServicesException("Failed to retrieve lesson by id", e);
        }
        return lesson;
    }

    public boolean updateLesson(@Valid Lesson lesson)  {
        if (lesson.getLessonId() == 0) {
            throw new ServicesException("Missing id");
        }

        Set<ConstraintViolation<Lesson>> constraintViolations = validator.getValidatorInstance().validate(lesson);
        if (!constraintViolations.isEmpty()) {
            throw new ServicesException("Data is not valid: " + constraintViolations.iterator().next());
        }

        try {
            lessonDao.getById(lesson.getLessonId());
        } catch (DataAccessException e) {
            throw new ServicesException("Failed to retrieve lesson by such id", e);
        }

        try {
            return lessonDao.update(lesson);
        } catch (DataAccessException e) {
            throw new ServicesException("Problem with updating lesson");
        }
    }
}
