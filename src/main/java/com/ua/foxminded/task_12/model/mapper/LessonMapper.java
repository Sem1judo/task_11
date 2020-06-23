package com.ua.foxminded.task_12.model.mapper;


import com.ua.foxminded.task_12.model.Lesson;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LessonMapper implements RowMapper<Lesson> {

    @Override
    public Lesson mapRow(ResultSet resultSet, int i) throws SQLException {
        Lesson lesson = new Lesson();

        lesson.setLessonId(resultSet.getLong("lesson_id"));
        lesson.setName(resultSet.getString("name"));

        return lesson;
    }
}
