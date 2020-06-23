package com.ua.foxminded.task_12.model.mapper;

import com.ua.foxminded.task_12.model.Lector;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class LectorMapper implements RowMapper<Lector> {
    @Override
    public Lector mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Lector lector = new Lector();

        lector.setLectorId(resultSet.getLong("lector_id"));
        lector.setFirstName(resultSet.getString("first_name"));
        lector.setLastName(resultSet.getString("last_name"));

        return lector;
    }
}
