package com.ua.foxminded.task_11.services;

import com.ua.foxminded.task_11.dao.impl.LectorDaoImpl;
import com.ua.foxminded.task_11.exceptions.DaoException;
import com.ua.foxminded.task_11.exceptions.ServicesException;
import com.ua.foxminded.task_11.model.Faculty;
import com.ua.foxminded.task_11.model.Group;
import com.ua.foxminded.task_11.model.Lector;
import com.ua.foxminded.task_11.validation.ValidatorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class LectorServices {
    @Autowired
    private LectorDaoImpl lectorDao;
   @Autowired
   private ValidatorEntity validator;

    public List<Lector> getLectors() throws ServicesException {
        try {
            return lectorDao.getAll();
        } catch (DaoException e) {
            throw new ServicesException("Failed to get lectors", e);
        }
    }

    public void createNewLector(@Valid Lector lector) throws ServicesException {
        try {
            lectorDao.create(lector);
        } catch (DaoException e) {
            throw new ServicesException("Failed to create lector", e);
        }
    }

    public boolean deleteLector(long id) throws ServicesException {
        try {
          return lectorDao.delete(id);
        } catch (DaoException e) {
            throw new ServicesException("Failed to delete lector by id", e);
        }
    }

    public Lector getLector(long id) throws ServicesException {
        Lector lector;
        try {
            lector = lectorDao.getById(id);
        } catch (DaoException e) {
            throw new ServicesException("Failed to retrieve lector by id", e);
        }
        return lector;
    }

    public boolean updateLector(@Valid Lector lector) throws ServicesException {
        if (lector.getLectorId() == 0) {
            throw new ServicesException("Missing id");
        }

        Set<ConstraintViolation<Lector>> constraintViolations = validator.getValidatorInstance().validate(lector);
        if (!constraintViolations.isEmpty()) {
            throw new ServicesException("Data is not valid: " + constraintViolations.iterator().next());
        }

        try {
            lectorDao.getById(lector.getLectorId());
        } catch (DaoException e) {
            throw new ServicesException("Failed to retrieve lector by id", e);
        }

        try {
            return lectorDao.update(lector);
        } catch (DaoException e) {
            throw new ServicesException("Problem with updating lector");
        }
    }

    public int getLessonsForLector(LocalDateTime start, LocalDateTime end) throws ServicesException {
        try {
            return lectorDao.getLessonsByTime(start, end);
        } catch (DaoException e) {
            throw new ServicesException("Failed to get lessons for lector by id", e);
        }
    }
}

