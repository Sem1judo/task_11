package com.ua.foxminded.task_11.services;

import com.ua.foxminded.task_11.dao.impl.TimeSlotDaoImpl;
import com.ua.foxminded.task_11.exceptions.ServiceException;

import com.ua.foxminded.task_11.model.*;
import com.ua.foxminded.task_11.validation.ValidatorEntity;
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

    public List<TimeSlot> getAll() {
        try {
            return timeSlotDao.getAll();
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchEntityException("Doesn't exist such time slots");
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to get time slots", e);
        }
    }

    public boolean create(TimeSlot timeSlot) {
        validator.validate(timeSlot);
        try {
            return timeSlotDao.create(timeSlot);
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to create time slot", e);
        }
    }

    public boolean delete(long id) {
        if (id == 0) {
            throw new ServiceException("Missing id");
        }
        try {
            return timeSlotDao.delete(id);
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to delete time slot by id", e);
        }
    }

    public TimeSlot getById(long id) {
        if (id == 0) {
            throw new ServiceException("Missing id");
        }
        TimeSlot timeSlot;
        try {
            timeSlot = timeSlotDao.getById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchEntityException("Doesn't exist such faculties");
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to retrieve time slot by id", e);
        }
        return timeSlot;
    }

    public boolean update(TimeSlot timeSlot) {
        if (timeSlot.getTimeSlotId() == 0) {
            throw new ServiceException("Missing id");
        }
        validator.validate(timeSlot);
        try {
            timeSlotDao.getById(timeSlot.getTimeSlotId());
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchEntityException("Doesn't exist such time slot");
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to retrieve lesson by such id:", e);
        }

        try {
            return timeSlotDao.update(timeSlot);
        } catch (DataAccessException e) {
            throw new ServiceException("Problem with updating lesson");
        }
    }
}

