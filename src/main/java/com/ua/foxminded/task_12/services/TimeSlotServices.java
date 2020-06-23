package com.ua.foxminded.task_12.services;

import com.ua.foxminded.task_12.dao.impl.TimeSlotDaoImpl;
import com.ua.foxminded.task_12.exceptions.ServiceException;

import com.ua.foxminded.task_12.model.*;
import com.ua.foxminded.task_12.validation.ValidatorEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.ejb.NoSuchEntityException;
import java.util.List;


@Service
@Scope(value = "session")
public class TimeSlotServices {

    @Autowired
    private TimeSlotDaoImpl timeSlotDao;

    @Autowired
    private ValidatorEntity<TimeSlot> validator;

    private static final Logger logger = LoggerFactory.getLogger(TimeSlotServices.class);

    private static final String MISSING_ID = "Missing id time slot.";
    private static final String NOT_EXIST_ENTITY = "Doesn't exist such time slot";


    public List<TimeSlot> getAll() {
        logger.debug("Trying to get all time slots");

        try {
            return timeSlotDao.getAll();
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Time slots is not exist");
            throw new NoSuchEntityException("Doesn't exist such time slots");
        } catch (DataAccessException e) {
            logger.error("Failed to get all time slots", e);
            throw new ServiceException("Failed to get list of time slots", e);
        }
    }

    public boolean create(TimeSlot timeSlot) {
        logger.debug("Trying to create time slot: {}", timeSlot);

        validator.validate(timeSlot);
        try {
            return timeSlotDao.create(timeSlot);
        } catch (DataAccessException e) {
            logger.error("Failed to create time slot: {}", timeSlot, e);
            throw new ServiceException("Failed to create time slot", e);
        }
    }

    public boolean delete(long id) {
        logger.debug("Trying to delete time slot id={}", id);

        if (id == 0) {
            logger.warn(MISSING_ID);
            throw new ServiceException(MISSING_ID);
        }
        try {
            return timeSlotDao.delete(id);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Not existing time slot with id={}", id);
            throw new NoSuchEntityException(NOT_EXIST_ENTITY);
        } catch (DataAccessException e) {
            logger.error("Failed to delete lesson with id={}", id, e);
            throw new ServiceException("Failed to delete time slot by id", e);
        }
    }

    public TimeSlot getById(long id) {
        logger.debug("Trying to get time slot with id={}", id);

        if (id == 0) {
            logger.warn(MISSING_ID);
            throw new ServiceException(MISSING_ID);
        }
        TimeSlot timeSlot;
        try {
            timeSlot = timeSlotDao.getById(id);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Not existing time slot with id={}", id);
            throw new NoSuchEntityException(NOT_EXIST_ENTITY);
        } catch (DataAccessException e) {
            logger.error("Failed to retrieve time slot with id={}", id, e);
            throw new ServiceException("Failed to retrieve time slot by id", e);
        }
        return timeSlot;
    }

    public boolean update(TimeSlot timeSlot) {
        logger.debug("Trying to update time slot: {}", timeSlot);

        if (timeSlot.getTimeSlotId() == 0) {
            logger.warn(MISSING_ID);
            throw new ServiceException(MISSING_ID);
        }
        validator.validate(timeSlot);
        try {
            timeSlotDao.getById(timeSlot.getTimeSlotId());
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Not existing time slot: {}", timeSlot);
            throw new NoSuchEntityException(NOT_EXIST_ENTITY);
        } catch (DataAccessException e) {
            logger.error("Failed to retrieve time slot: {}", timeSlot, e);
            throw new ServiceException("Failed to retrieve time slot:", e);
        }
        try {
            return timeSlotDao.update(timeSlot);
        } catch (DataAccessException e) {
            logger.error("Failed to update time slot: {}", timeSlot, e);
            throw new ServiceException("Problem with updating time slot");
        }
    }
}

