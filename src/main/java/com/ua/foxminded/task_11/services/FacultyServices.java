package com.ua.foxminded.task_11.services;

import com.ua.foxminded.task_11.dao.impl.FacultyDaoImpl;
import com.ua.foxminded.task_11.exceptions.DaoException;
import com.ua.foxminded.task_11.exceptions.ServicesException;
import com.ua.foxminded.task_11.exceptions.WrongInputDataException;
import com.ua.foxminded.task_11.model.Faculty;
import com.ua.foxminded.task_11.model.Group;
import com.ua.foxminded.task_11.model.Lector;
import com.ua.foxminded.task_11.validation.impl.FacultyValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.validation.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class FacultyServices {

    @Autowired
    private FacultyDaoImpl facultyDao;

    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private Validator validator = factory.getValidator();


    public List<Faculty> getFaculties() {
        try {
            return facultyDao.getAll();
        } catch (DaoException e) {
            throw new ServicesException("Failed to create faculty", e);
        }
    }

    public void createNewFaculty(Faculty faculty) {
        try {
            facultyDao.create(faculty);
        } catch (DaoException e) {
            throw new ServicesException("Failed to create faculty", e);
        }
    }

    public void deleteFaculty(long id) {
        try {
            facultyDao.delete(id);
        } catch (DaoException e) {
            throw new ServicesException("Failed to delete faculty from id", e);
        }
    }

    public Faculty getFaculty(long id) throws ServicesException {
        Faculty faculty;
        try {
            faculty = facultyDao.getById(id);
        } catch (DaoException e) {
            throw new ServicesException("Failed to retrieve faculty from id", e);
        }
        return faculty;
    }

    public boolean updateFaculty(Faculty faculty)
            throws ServicesException {

        if (faculty.getFacultyId() == 0) {
            throw new ServicesException("Missing id");
        }

        validator.validate(faculty)
                .forEach(violation -> System.out.println(violation.getMessage()));

        try {
            facultyDao.getById(faculty.getFacultyId());
        } catch (DaoException e) {
            throw new ServicesException("Failed to retrieve faculty from id", e);
        }

        try {
            return facultyDao.update(faculty);
        } catch (DaoException e) {
            throw new ServicesException("Problem with updating faculty");
        }
    }
}


