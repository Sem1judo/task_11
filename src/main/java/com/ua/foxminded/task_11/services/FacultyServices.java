package com.ua.foxminded.task_11.services;

import com.ua.foxminded.task_11.dao.impl.FacultyDaoImpl;
import com.ua.foxminded.task_11.exceptions.ServicesException;
import com.ua.foxminded.task_11.model.Faculty;
import com.ua.foxminded.task_11.validation.ValidatorEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.validation.*;
import java.util.List;
import java.util.Set;


@Service
public class FacultyServices {

    @Autowired
    private FacultyDaoImpl facultyDao;

    @Autowired
    private ValidatorEntity validator;


    public List<Faculty> getFaculties() {
        try {
            return facultyDao.getAll();
        } catch (DataAccessException e) {
            throw new ServicesException("Failed to get list of faculties", e);
        }
    }

    public boolean createNewFaculty(@Valid Faculty faculty) {
        try {
            return facultyDao.create(faculty);
        } catch (DataAccessException e) {
            throw new ServicesException("Failed to create new faculty", e);
        }
    }

    public boolean deleteFaculty(long id) {
        if (id == 0) {
            throw new ServicesException("Missing id");
        }
        try {
            return facultyDao.delete(id);
        } catch (DataAccessException e) {
            throw new ServicesException("Failed to delete faculty by id", e);
        }
    }

    public Faculty getFaculty(long id) {
        if (id == 0) {
            throw new ServicesException("Missing id");
        }
        Faculty faculty;
        try {
            faculty = facultyDao.getById(id);
        } catch (DataAccessException e) {
            throw new ServicesException("Failed to retrieve faculty with such id", e);
        }
        return faculty;
    }

    public boolean updateFaculty(@Valid Faculty faculty) {
        if (faculty.getFacultyId() == 0) {
            throw new ServicesException("Missing id");
        }

        Set<ConstraintViolation<Faculty>> constraintViolations = validator.getValidatorInstance().validate(faculty);
        if (!constraintViolations.isEmpty()) {
            throw new ServicesException("Data is not valid: " + constraintViolations.iterator().next());
        }

        try {
            facultyDao.getById(faculty.getFacultyId());
        } catch (DataAccessException e) {
            throw new ServicesException("Failed to retrieve faculty with such id: ", e);
        }

        try {
            return facultyDao.update(faculty);
        } catch (DataAccessException e) {
            throw new ServicesException("Problem with updating faculty");
        }
    }
}


