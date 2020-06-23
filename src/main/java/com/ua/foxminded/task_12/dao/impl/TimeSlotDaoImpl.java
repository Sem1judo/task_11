package com.ua.foxminded.task_12.dao.impl;

import com.ua.foxminded.task_12.dao.DaoEntity;
import com.ua.foxminded.task_12.model.TimeSlot;
import com.ua.foxminded.task_12.model.mapper.TimeSLotMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TimeSlotDaoImpl implements DaoEntity<TimeSlot> {

    @Autowired
    @Qualifier("jdbcTemplate")
    JdbcTemplate jdbcTemplate;

    private static final String SQL_FIND_TIMESLOT = "select * from time_slots where timeslot_id = ?";
    private static final String SQL_DELETE_TIMESLOT = "delete from time_slots where timeslot_id = ?";
    private static final String SQL_UPDATE_TIMESLOT = "update time_slots set start_lesson = ?, end_lesson = ? where timeslot_id = ?";
    private static final String SQL_GET_ALL_TIMESLOT = "select * from lessons";
    private static final String SQL_INSERT_TIMESLOT = "insert into time_slots(start_lesson, end_lesson) values(?,?)";


    @Override
    public TimeSlot getById(Long id) {
        return jdbcTemplate.queryForObject(SQL_FIND_TIMESLOT, new Object[]{id}, new TimeSLotMapper());
    }

    @Override
    public List<TimeSlot> getAll() {
        return jdbcTemplate.query(SQL_GET_ALL_TIMESLOT, new TimeSLotMapper());
    }

    @Override
    public boolean delete(Long id) {
        return jdbcTemplate.update(SQL_DELETE_TIMESLOT, id) > 0;
    }

    @Override
    public boolean update(TimeSlot timeSlot) {
        return jdbcTemplate.update(SQL_UPDATE_TIMESLOT, timeSlot.getStartLesson(), timeSlot.getEndLesson(),
                timeSlot.getTimeSlotId()) > 0;
    }

    @Override
    public boolean create(TimeSlot timeSlot) {
        return jdbcTemplate.update(SQL_INSERT_TIMESLOT, timeSlot.getStartLesson(), timeSlot.getEndLesson()) > 0;

    }
}
