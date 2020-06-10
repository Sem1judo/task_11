package com.ua.foxminded.task_11.services;

import com.ua.foxminded.task_11.dao.impl.TimeSlotDaoImpl;
import com.ua.foxminded.task_11.exceptions.ServicesException;

import com.ua.foxminded.task_11.model.*;
import com.ua.foxminded.task_11.validation.ValidatorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.validation.*;

import java.util.List;
import java.util.Set;


@Service
@Scope(value = "session")
public class TimeSlotServices {

    @Autowired
    private TimeSlotDaoImpl timeSlotDao;

    @Autowired
    private ValidatorEntity validator;

    public List<TimeSlot> getTimeSlots() {
        try {
            return timeSlotDao.getAll();
        } catch (DataAccessException e) {
            throw new ServicesException("Failed to get time slots", e);
        }
    }


    public boolean createNewTimeSlot(@Valid TimeSlot timeSlot) {
        try {
            return timeSlotDao.create(timeSlot);
        } catch (DataAccessException e) {
            throw new ServicesException("Failed to create time slot", e);
        }
    }

    public boolean deleteTimeSlot(long id) {
        if (id == 0) {
            throw new ServicesException("Missing id");
        }
        try {
            return timeSlotDao.delete(id);
        } catch (DataAccessException e) {
            throw new ServicesException("Failed to delete time slot by id", e);
        }
    }

    public TimeSlot getTimeSlot(long id) {
        if (id == 0) {
            throw new ServicesException("Missing id");
        }
        TimeSlot timeSlot;
        try {
            timeSlot = timeSlotDao.getById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ServicesException("Failed to retrieve time slot by id", e);
        }
        return timeSlot;
    }

    public boolean updateTimeSlot(@Valid TimeSlot timeSlot) {
        if (timeSlot.getTimeSlotId() == 0) {
            throw new ServicesException("Missing id");
        }

        Set<ConstraintViolation<TimeSlot>> constraintViolations = validator.getValidatorInstance().validate(timeSlot);
        if (!constraintViolations.isEmpty()) {
            throw new ServicesException("Data is not valid: " + constraintViolations.iterator().next());
        }

        try {
            timeSlotDao.getById(timeSlot.getTimeSlotId());
        } catch (DataAccessException e) {
            throw new ServicesException("Failed to retrieve lesson by such id:", e);
        }

        try {
            return timeSlotDao.update(timeSlot);
        } catch (DataAccessException e) {
            throw new ServicesException("Problem with updating lesson");
        }
    }
}

