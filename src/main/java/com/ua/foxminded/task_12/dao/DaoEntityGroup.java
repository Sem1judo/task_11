package com.ua.foxminded.task_12.dao;

import com.ua.foxminded.task_12.model.Group;

public interface DaoEntityGroup extends DaoEntity<Group> {
     int getLessonsById(Long id);
}
