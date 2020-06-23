package com.ua.foxminded.task_12.services;

import com.ua.foxminded.task_12.dao.impl.LectorDaoImpl;
import com.ua.foxminded.task_12.exceptions.ServiceException;
import com.ua.foxminded.task_12.model.Lector;
import com.ua.foxminded.task_12.validation.ValidatorEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(LectorServices.class);

    private static final String MISSING_ID = "Missing id lector.";

    public List<Lector> getAll() {
        logger.debug("Trying to get all lectors");
        try {
            return lectorDao.getAll();
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Lectors is not exist");
            throw new NoSuchEntityException("Doesn't exist such lectors");
        } catch (DataAccessException e) {
            logger.error("Failed to get all lectors", e);
            throw new ServiceException("Failed to get list of lector", e);
        }
    }

    public boolean create(Lector lector) {
        logger.debug("Trying to create lector: {}", lector);

        validator.validate(lector);
        try {
            return lectorDao.create(lector);
        } catch (
                DataAccessException e) {
            logger.error("failed to create lector: {}", lector, e);
            throw new ServiceException("Failed to create lector", e);
        }

    }

    public boolean delete(long id) {
        logger.debug("Trying to delete lector with id={}", id);

        if (id == 0) {
            logger.warn(MISSING_ID);
            throw new ServiceException(MISSING_ID);
        }
        try {
            return lectorDao.delete(id);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Is not existing lector with id={}", id);
            throw new NoSuchEntityException("Doesn't exist such lector");
        } catch (DataAccessException e) {
            logger.error("failed to delete lector with id={}", id, e);
            throw new ServiceException("Failed to delete lector by such id", e);
        }
    }

    public Lector getById(long id) {
        logger.debug("Trying to get lector with id={}", id);

        if (id == 0) {
            logger.warn(MISSING_ID);
            throw new ServiceException(MISSING_ID);
        }

        Lector lector;
        try {
            lector = lectorDao.getById(id);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Is not existing lector with id={}", id);
            throw new NoSuchEntityException("Doesn't exist such lector");
        } catch (DataAccessException e) {
            logger.error("failed to retrieve lector with id={}", id, e);
            throw new ServiceException("Failed to retrieve lector by such id: ", e);
        }
        return lector;
    }

    public boolean update(Lector lector) {
        logger.debug("Trying to update lector: {}", lector);

        if (lector.getLectorId() == 0) {
            logger.warn(MISSING_ID);
            throw new ServiceException(MISSING_ID);
        }
        validator.validate(lector);
        try {
            lectorDao.getById(lector.getLectorId());
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Is not existing lector: {}", lector);
            throw new NoSuchEntityException("Doesn't exist such lector");
        } catch (DataAccessException e) {
            logger.error("Failed to retrieve lector: {}", lector, e);
            throw new ServiceException("Failed to retrieve lector by id" + e);
        }

        try {
            return lectorDao.update(lector);
        } catch (DataAccessException e) {
            logger.error("Failed to update lector: {}", lector, e);
            throw new ServiceException("Problem with updating lector");
        }
    }

    public int getLessonsForLector(LocalDateTime start, LocalDateTime end) {
        logger.debug("Trying to get lessons for lector with start time={} and end time={}", start, end);

        try {
            return lectorDao.getLessonsByTime(start, end);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Is not existing lessons with such: start time={} and end time={}", start, end);
            throw new NoSuchEntityException("Doesn't exist such lessons for lector");
        } catch (DataAccessException e) {
            logger.error("Failed to get lessons for lector with start time={} and end time={}", start, end, e);
            throw new ServiceException("Failed to get lessons for lector by id", e);
        }
    }
}

