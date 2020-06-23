package com.ua.foxminded.task_12.dao;

import java.util.List;

public interface DaoEntity<T> {

    T getById(Long id);

    List<T> getAll();

    boolean delete(Long id);

    boolean update(T entity);

    boolean create(T entity);


}
