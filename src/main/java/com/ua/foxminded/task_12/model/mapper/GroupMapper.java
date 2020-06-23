package com.ua.foxminded.task_12.model.mapper;

import com.ua.foxminded.task_12.model.Group;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupMapper implements RowMapper<Group> {
    @Override
    public Group mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Group group = new Group();

        group.setGroupId(resultSet.getLong("group_id"));
        group.setName(resultSet.getString("name"));

        return group;
    }
}

