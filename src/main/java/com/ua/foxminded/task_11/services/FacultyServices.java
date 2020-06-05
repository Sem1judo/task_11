package com.ua.foxminded.task_11.services;

import com.ua.foxminded.task_11.dao.impl.FacultyDaoImpl;
import com.ua.foxminded.task_11.model.Faculty;
import com.ua.foxminded.task_11.model.Group;
import com.ua.foxminded.task_11.model.Lector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Component
public class FacultyServices {

    @Autowired
    private FacultyDaoImpl facultyDao;

    public Optional<List<Faculty>> getFaculties() {
        return Optional.ofNullable(facultyDao.getAll());
    }

    public void createNewFaculty(@Valid String nameFaculty, List<Group> groups, List<Lector> lectors) {
        Faculty faculty = new Faculty();
        faculty.setName(nameFaculty);
        faculty.setGroups(groups);
        faculty.setLectors(lectors);
        facultyDao.create(faculty);
    }

    public void deleteFaculty(long id) {
        facultyDao.delete(id);
    }

    public Optional<Faculty> getFaculty(long id) {
        return Optional.ofNullable(facultyDao.getById(id));
    }

    public void updateFaculty(long id, String nameFaculty, List<Group> groups, List<Lector> lectors) {
        Faculty faculty = facultyDao.getById(id);
        faculty.setName(nameFaculty);
        faculty.setGroups(groups);
        faculty.setLectors(lectors);
        facultyDao.update(faculty);
    }

}
