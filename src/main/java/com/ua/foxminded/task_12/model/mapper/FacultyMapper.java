package com.ua.foxminded.task_12.model.mapper;

import com.ua.foxminded.task_12.model.Faculty;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FacultyMapper implements RowMapper<Faculty> {
    @Override
    public Faculty mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Faculty faculty = new Faculty();

        faculty.setFacultyId(resultSet.getLong("faculty_id"));
        faculty.setName(resultSet.getString("faculty_name"));

        return faculty;
    }
}
