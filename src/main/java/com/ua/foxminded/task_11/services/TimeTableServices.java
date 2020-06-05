package com.ua.foxminded.task_11.services;

import com.ua.foxminded.task_11.dao.impl.TimeSlotDaoImpl;
import com.ua.foxminded.task_11.model.Group;
import com.ua.foxminded.task_11.model.Lector;
import com.ua.foxminded.task_11.model.TimeSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Scope(value = "session")
public class TimeTableServices {
    @Autowired
    private TimeSlotDaoImpl timeSlotDao;

    public Optional<List<TimeSlot>> getTimeSlots() {
        return Optional.ofNullable(timeSlotDao.getAll());
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

    public Optional<TimeSlot> getTimeSlot(long id) {
        return Optional.ofNullable(timeSlotDao.getById(id));
    }

    public void updateTimeSlot(long id, @Valid LocalDateTime startLesson, @Valid LocalDateTime endLesson, Lector lector, Group group) {
        TimeSlot timeSlot = timeSlotDao.getById(id);
        timeSlot.setStartLesson(startLesson);
        timeSlot.setEndLesson(endLesson);
        timeSlot.setLector(lector);
        timeSlot.setGroup(group);
        timeSlotDao.update(timeSlot);
    }
}
