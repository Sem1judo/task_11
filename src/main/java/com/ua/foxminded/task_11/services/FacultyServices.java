package com.ua.foxminded.task_11.services;

import com.ua.foxminded.task_11.dao.impl.FacultyDaoImpl;
import com.ua.foxminded.task_11.model.Faculty;
import com.ua.foxminded.task_11.model.Group;
import com.ua.foxminded.task_11.model.Lector;

import java.util.List;

public class FacultyServices {
    private FacultyDaoImpl facultyDao = new FacultyDaoImpl();

    public List<Faculty> getFaculties() {
        return facultyDao.getAll();
    }

    public void createNewFaculty(String nameFaculty, List<Group> groups, List<Lector> lectors) {
        Faculty faculty = new Faculty();
        faculty.setName(nameFaculty);
        faculty.setGroups(groups);
        faculty.setLectors(lectors);
        facultyDao.create(faculty);
    }

    public void deleteFaculty(long id) {
        facultyDao.delete(id);
    }

    public Faculty getFaculty(long id) {
        return facultyDao.getById(id);
    }

    public void updateFaculty(long id, String nameFaculty, List<Group> groups, List<Lector> lectors) {
        Faculty faculty = facultyDao.getById(id);
        faculty.setName(nameFaculty);
        faculty.setGroups(groups);
        faculty.setLectors(lectors);
        facultyDao.update(faculty);
    }

}