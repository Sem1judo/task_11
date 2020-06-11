package com.ua.foxminded.task_11.services;

import com.sun.tools.internal.ws.wsdl.framework.NoSuchEntityException;
import com.ua.foxminded.task_11.dao.impl.FacultyDaoImpl;
import com.ua.foxminded.task_11.exceptions.ServiceException;
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
    private ValidatorEntity<Faculty> validator;


    public List<Faculty> getAll() {
        try {
            return facultyDao.getAll();
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchEntityException("Doesn't exist such faculties");
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to get list of faculties", e);
        }
    }

    public boolean create(@Valid Faculty faculty) {
        try {
            validator.validate(faculty);
        } catch (ServiceException e) {
            throw new ServiceException("Don't pass validation");
        }
        try {
            return facultyDao.create(faculty);
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to create new faculty", e);
        }
    }

    public boolean delete(long id) {
        if (id == 0) {
            throw new ServiceException("Missing id");
        }
        try {
            return facultyDao.delete(id);
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to delete faculty by id", e);
        }
    }

    public Faculty getById(long id) {
        if (id == 0) {
            throw new ServiceException("Missing id");
        }
        Faculty faculty;
        try {
            faculty = facultyDao.getById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchEntityException("Doesn't exist such faculty");
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to retrieve faculty with such id", e);
        }
        return faculty;
    }

    public boolean update(@Valid Faculty faculty) {
        if (faculty.getFacultyId() == 0) {
            throw new ServiceException("Missing id");
        }
        try {
            validator.validate(faculty);
        } catch (ServiceException e) {
            throw new ServiceException("Don't pass validation");
        }
        try {
            facultyDao.getById(faculty.getFacultyId());
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to retrieve faculty with such id: ", e);
        }

        try {
            return facultyDao.update(faculty);
        } catch (DataAccessException e) {
            throw new ServiceException("Problem with updating faculty");
        }
    }
}


