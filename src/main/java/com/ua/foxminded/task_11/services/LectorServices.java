package com.ua.foxminded.task_11.services;

import com.ua.foxminded.task_11.dao.impl.LectorDaoImpl;
import com.ua.foxminded.task_11.exceptions.ServiceException;
import com.ua.foxminded.task_11.model.Lector;
import com.ua.foxminded.task_11.validation.ValidatorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.ejb.NoSuchEntityException;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class LectorServices {
    @Autowired
    private LectorDaoImpl lectorDao;
    @Autowired
    private ValidatorEntity<Lector> validator;

    public List<Lector> getAll() {
        try {
            return lectorDao.getAll();
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchEntityException("Doesn't exist such lectors");
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to get list of lectors", e);
        }
    }

    public boolean create(Lector lector) {
        validator.validate(lector);
        try {
            return lectorDao.create(lector);
        } catch (
                DataAccessException e) {
            throw new ServiceException("Failed to create lector", e);
        }

    }

    public boolean delete(long id) {
        if (id == 0) {
            throw new ServiceException("Missing id");
        }
        try {
            return lectorDao.delete(id);
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to delete lector by such id", e);
        }
    }

    public Lector getById(long id) {
        if (id == 0) {
            throw new ServiceException("Missing id");
        }

        Lector lector;
        try {
            lector = lectorDao.getById(id);
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to retrieve lector by such id: ", e);
        }
        return lector;
    }

    public boolean update(Lector lector) {
        if (lector.getLectorId() == 0) {
            throw new ServiceException("Missing id");
        }
        validator.validate(lector);
        try {
            lectorDao.getById(lector.getLectorId());
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to retrieve lector by id" + e);
        }

        try {
            return lectorDao.update(lector);
        } catch (DataAccessException e) {
            throw new ServiceException("Problem with updating lector");
        }
    }

    public int getLessonsForLector(LocalDateTime start, LocalDateTime end) {
        try {
            return lectorDao.getLessonsByTime(start, end);
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchEntityException("Doesn't exist such lessons for lector");
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to get lessons for lector by id", e);
        }
    }
}

