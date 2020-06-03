package com.ua.foxminded.task_11.dao;

import com.ua.foxminded.task_11.model.Lector;

import java.time.LocalDateTime;

public interface DaoEntityLector extends DaoEntity<Lector> {

     int getLessonsByTime(LocalDateTime start, LocalDateTime end);
}

