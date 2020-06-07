package com.ua.foxminded.task_11.services;

import com.ua.foxminded.task_11.dao.impl.TimeSlotDaoImpl;
import com.ua.foxminded.task_11.exceptions.DaoException;
import com.ua.foxminded.task_11.exceptions.ServicesException;

import com.ua.foxminded.task_11.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.List;


@Component
@Scope(value = "session")
public class TimeSlotServices {

    @Autowired
    private TimeSlotDaoImpl timeSlotDao;

    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private Validator validator = factory.getValidator();

    public List<TimeSlot> getTimeSlots() throws ServicesException {
        try {
            return timeSlotDao.getAll();
        } catch (DaoException e) {
            throw new ServicesException("Failed to get time slots", e);
        }
    }


    public boolean createNewTimeSlot(@Valid TimeSlot timeSlot) throws ServicesException {
        try {
           return timeSlotDao.create(timeSlot);
        } catch (DaoException e) {
            throw new ServicesException("Failed to create time slot", e);
        }
    }

    public boolean deleteTimeSlot(long id) throws ServicesException {
        try {
            return timeSlotDao.delete(id);
        } catch (DaoException e) {
            throw new ServicesException("Failed to delete time slot by id", e);
        }
    }

    public TimeSlot getTimeSlot(long id) throws ServicesException {
        TimeSlot timeSlot;
        try {
            timeSlot = timeSlotDao.getById(id);
        } catch (DaoException e) {
            throw new ServicesException("Failed to retrieve time slot by id", e);
        }
        return timeSlot;
    }

    public boolean updateTimeSlot(@Valid TimeSlot timeSlot) throws ServicesException {
        if (timeSlot.getTimeSlotId() == 0) {
            throw new ServicesException("Missing id");
        }

        validator.validate(timeSlot)
                .forEach(violation -> System.out.println(violation.getMessage()));

        try {
            timeSlotDao.getById(timeSlot.getTimeSlotId());
        } catch (DaoException e) {
            throw new ServicesException("Failed to retrieve lesson by id", e);
        }

        try {
            return timeSlotDao.update(timeSlot);
        } catch (DaoException e) {
            throw new ServicesException("Problem with updating lesson");
        }
    }
}

