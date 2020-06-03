package com.ua.foxminded.task_11.dao;

import com.ua.foxminded.task_11.model.Group;

public interface DaoEntityGroup extends DaoEntity<Group> {
     int getLessonsById(Long id);
}
