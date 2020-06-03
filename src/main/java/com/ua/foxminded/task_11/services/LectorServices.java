package com.ua.foxminded.task_11.services;

import com.ua.foxminded.task_11.dao.impl.LectorDaoImpl;
import com.ua.foxminded.task_11.model.Lector;

import java.time.LocalDateTime;
import java.util.List;

public class LectorServices {
    private LectorDaoImpl lectorDao = new LectorDaoImpl();

    public List<Lector> getLectors() {
        return lectorDao.getAll();
    }

    public void createNewLector(String firstName,String lastName, long facultyId) {
        Lector lector = new Lector();
        lector.setFirstName(firstName);
        lector.setLastName(lastName);
        lector.setFacultyId(facultyId);
        lectorDao.create(lector);
    }

    public void deleteLector(long id) {

        lectorDao.delete(id);
    }

    public Lector getLector(long id) {
        return lectorDao.getById(id);
    }

    public void updateLector(long id, String firstName,String lastName, long facultyId) {
            Lector lector = lectorDao.getById(id);
        lector.setFacultyId(facultyId);
        lector.setFirstName(firstName);
        lector.setLastName(lastName);
        lectorDao.update(lector);
    }
    public int getLessonsForLector(LocalDateTime start, LocalDateTime end){
      return lectorDao.getLessonsByTime(start,end);
    }
}
