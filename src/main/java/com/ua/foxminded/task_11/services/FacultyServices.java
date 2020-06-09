package com.ua.foxminded.task_11.services;

import com.ua.foxminded.task_11.dao.impl.FacultyDaoImpl;
import com.ua.foxminded.task_11.exceptions.DaoException;
import com.ua.foxminded.task_11.exceptions.ServicesException;
import com.ua.foxminded.task_11.model.Faculty;

import com.ua.foxminded.task_11.validation.ValidatorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.*;
import java.util.List;
import java.util.Set;


@Component
public class FacultyServices {

    @Autowired
    private FacultyDaoImpl facultyDao;

    @Autowired
    private ValidatorEntity validator;


    public List<Faculty> getFaculties() {
        try {
            return facultyDao.getAll();
        } catch (DaoException e) {
            throw new ServicesException("Failed to get faculties", e);
        }
    }

    public boolean createNewFaculty(@Valid Faculty faculty) {
        try {
            return facultyDao.create(faculty);
        } catch (DaoException e) {
            throw new ServicesException("Failed to create faculty", e);
        }
    }

    public boolean deleteFaculty(long id) {
        try {
            return facultyDao.delete(id);
        } catch (DaoException e) {
            throw new ServicesException("Failed to delete faculty by id", e);
        }
    }

    public Faculty getFaculty(long id) throws ServicesException {
        Faculty faculty;
        try {
            faculty = facultyDao.getById(id);
        } catch (DaoException e) {
            throw new ServicesException("Failed to retrieve faculty by id", e);
        }
        return faculty;
    }

    public boolean updateFaculty(Faculty faculty) throws ServicesException {
        if (faculty.getFacultyId() == 0) {
            throw new ServicesException("Missing id");
        }

        Set<ConstraintViolation<Faculty>> constraintViolations = validator.getValidatorInstance().validate(faculty);
        if (!constraintViolations.isEmpty()) {
            throw new ServicesException("Data is not valid: " + constraintViolations.iterator().next());
        }

        try {
            facultyDao.getById(faculty.getFacultyId());
        } catch (DaoException e) {
            throw new ServicesException("Failed to retrieve faculty by id", e);
        }

        try {
            return facultyDao.update(faculty);
        } catch (DaoException e) {
            throw new ServicesException("Problem with updating faculty");
        }
    }
}


