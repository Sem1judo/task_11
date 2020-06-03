package com.ua.foxminded.task_11.dao;

import java.util.List;

public interface DaoEntity<T> {

    T getById(Long id);

    List<T> getAll();

    boolean delete(Long id);

    boolean update(T entity);

    boolean create(T entity);


}
