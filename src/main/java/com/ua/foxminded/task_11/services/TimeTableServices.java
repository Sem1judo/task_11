package com.ua.foxminded.task_11.services;

import com.ua.foxminded.task_11.dao.impl.TimeSlotDaoImpl;
import com.ua.foxminded.task_11.model.Group;
import com.ua.foxminded.task_11.model.Lector;
import com.ua.foxminded.task_11.model.TimeSlot;

import java.time.LocalDateTime;
import java.util.List;

public class TimeTableServices {
    private TimeSlotDaoImpl timeSlotDao = new TimeSlotDaoImpl();

    public List<TimeSlot> getTimeSlots() {
        return timeSlotDao.getAll();
    }

    public void createNewTimeSlot(LocalDateTime startLesson, LocalDateTime endLesson, Lector lector, Group group) {
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setStartLesson(startLesson);
        timeSlot.setEndLesson(endLesson);
        timeSlot.setLector(lector);
        timeSlot.setGroup(group);
        timeSlotDao.create(timeSlot);
    }

    public void deleteTimeSlot(long id) {
        timeSlotDao.delete(id);
    }

    public TimeSlot getTimeSlot(long id) {
        return timeSlotDao.getById(id);
    }

    public void updateTimeSlot(long id, LocalDateTime startLesson, LocalDateTime endLesson, Lector lector, Group group) {
        TimeSlot timeSlot = timeSlotDao.getById(id);
        timeSlot.setStartLesson(startLesson);
        timeSlot.setEndLesson(endLesson);
        timeSlot.setLector(lector);
        timeSlot.setGroup(group);
        timeSlotDao.update(timeSlot);
    }
}
