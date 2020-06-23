package com.ua.foxminded.task_12.model.mapper;

import com.ua.foxminded.task_12.model.TimeSlot;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TimeSLotMapper implements RowMapper<TimeSlot> {
    @Override
    public TimeSlot mapRow(ResultSet resultSet, int i) throws SQLException {
        TimeSlot timeSLot = new TimeSlot();
        timeSLot.setTimeSlotId(resultSet.getLong("timeslot_id"));
        timeSLot.setStartLesson(resultSet.getTimestamp("start_lesson").toLocalDateTime());
        timeSLot.setEndLesson(resultSet.getTimestamp("end_lesson").toLocalDateTime());
        return null;
    }
}
