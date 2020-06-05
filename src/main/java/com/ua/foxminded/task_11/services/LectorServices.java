package com.ua.foxminded.task_11.services;

import com.ua.foxminded.task_11.dao.impl.LectorDaoImpl;
import com.ua.foxminded.task_11.model.Lector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class LectorServices {
    @Autowired
    private LectorDaoImpl lectorDao;

    public Optional<List<Lector>> getLectors() {
        return Optional.ofNullable(lectorDao.getAll());
    }

    public void createNewLector(@Valid String firstName, @Valid String lastName, long facultyId) {
        Lector lector = new Lector();
        lector.setFirstName(firstName);
        lector.setLastName(lastName);
        lector.setFacultyId(facultyId);
        lectorDao.create(lector);
    }

    public void deleteLector(long id) {
        lectorDao.delete(id);
    }

    public Optional<Lector> getLector(long id) {
        return Optional.ofNullable(lectorDao.getById(id));
    }

    public void updateLector(long id,@Valid  String firstName, @Valid String lastName, long facultyId) {
            Lector lector = lectorDao.getById(id);
        lector.setFacultyId(facultyId);
        lector.setFirstName(firstName);
        lector.setLastName(lastName);
        lectorDao.update(lector);
    }
    public Optional<Integer> getLessonsForLector(LocalDateTime start, LocalDateTime end){
      return Optional.of(lectorDao.getLessonsByTime(start, end));
    }
}
