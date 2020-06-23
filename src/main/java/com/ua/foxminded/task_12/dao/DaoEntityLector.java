package com.ua.foxminded.task_12.dao;

import com.ua.foxminded.task_12.model.Lector;

import java.time.LocalDateTime;

public interface DaoEntityLector extends DaoEntity<Lector> {

     int getLessonsByTime(LocalDateTime start, LocalDateTime end);
}

