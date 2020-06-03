package com.ua.foxminded.task_11.dao.impl;

import com.ua.foxminded.task_11.dao.DaoEntityLector;
import com.ua.foxminded.task_11.model.Lector;
import com.ua.foxminded.task_11.dao.DaoEntity;
import com.ua.foxminded.task_11.model.mapper.LectorMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;

@Repository
public class LectorDaoImpl implements DaoEntity<Lector>, DaoEntityLector {

    @Autowired
    @Qualifier("jdbcTemplate")
    JdbcTemplate jdbcTemplate;

    private static final String SQL_FIND_LECTORS = "select * from lectors where lector_id = ?";
    private static final String SQL_DELETE_LECTORS = "delete from lectors where lector_id = ?";
    private static final String SQL_UPDATE_LECTORS = "update lectors set first_name = ?, last_name = ? where lector_id = ?";
    private static final String SQL_GET_ALL_LECTORS = "select * from lectors";
    private static final String SQL_INSERT_LECTORS = "insert into lectors(first_name, last_name) values(?,?)";
    private static final String SQL_GET_LESSONS_LECTORS = "select count(lesson_id)*2 as quantity from time_slots\n" +
            "where time_slots.start_lesson between ? and ?";

    @Override
    public Lector getById(Long id) {
        return jdbcTemplate.queryForObject(SQL_FIND_LECTORS, new Object[]{id}, new LectorMapper());
    }

    @Override
    public List<Lector> getAll() {
        return jdbcTemplate.query(SQL_GET_ALL_LECTORS, new LectorMapper());
    }

    @Override
    public boolean delete(Long id) {
        return jdbcTemplate.update(SQL_DELETE_LECTORS, id) > 0;
    }

    @Override
    public boolean update(Lector lector) {
        return jdbcTemplate.update(SQL_UPDATE_LECTORS, lector.getFirstName(), lector.getLastName(),
                lector.getLectorId()) > 0;
    }

    @Override
    public boolean create(Lector lector) {
        return jdbcTemplate.update(SQL_INSERT_LECTORS, lector.getFirstName(), lector.getLastName()) > 0;
    }

    @Override
    public int getLessonsByTime(LocalDateTime start, LocalDateTime end) {
        return jdbcTemplate.queryForObject(SQL_GET_LESSONS_LECTORS, new Object[]{start, end}, Integer.class);
    }
}
