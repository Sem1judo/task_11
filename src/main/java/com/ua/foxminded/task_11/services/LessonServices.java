package com.ua.foxminded.task_11.services;


import com.ua.foxminded.task_11.dao.impl.LessonDaoImpl;
import com.ua.foxminded.task_11.exceptions.ServiceException;
import com.ua.foxminded.task_11.model.Lesson;
import com.ua.foxminded.task_11.validation.ValidatorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.ejb.NoSuchEntityException;
import java.util.List;


@Service
public class LessonServices {

    @Autowired
    private LessonDaoImpl lessonDao;
    @Autowired
    private ValidatorEntity<Lesson> validator;

    public List<Lesson> getAll() {
        try {
            return lessonDao.getAll();
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchEntityException("Doesn't exist such lessons");
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to get list of lessons", e);
        }
    }


    public boolean create(Lesson lesson) {
        validator.validate(lesson);
        try {
            return lessonDao.create(lesson);
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to create lesson", e);
        }
    }

    public boolean delete(long id) {
        if (id == 0) {
            throw new ServiceException("Missing id");
        }
        try {
            return lessonDao.delete(id);
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to delete lesson by id", e);
        }
    }


    public Lesson getById(long id) {
        if (id == 0) {
            throw new ServiceException("Missing id");
        }
        Lesson lesson;
        try {
            lesson = lessonDao.getById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchEntityException("Doesn't exist such faculties");
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to retrieve lesson by id", e);
        }
        return lesson;
    }

    public boolean update(Lesson lesson) {
        if (lesson.getLessonId() == 0) {
            throw new ServiceException("Missing id");
        }
        validator.validate(lesson);
        try {
            lessonDao.getById(lesson.getLessonId());
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to retrieve lesson by such id", e);
        }

        try {
            return lessonDao.update(lesson);
        } catch (DataAccessException e) {
            throw new ServiceException("Problem with updating lesson");
        }
    }
}
