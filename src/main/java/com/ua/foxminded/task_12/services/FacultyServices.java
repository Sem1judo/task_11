package com.ua.foxminded.task_12.services;

import com.ua.foxminded.task_12.dao.impl.FacultyDaoImpl;
import com.ua.foxminded.task_12.exceptions.ServiceException;
import com.ua.foxminded.task_12.model.Faculty;
import com.ua.foxminded.task_12.validation.ValidatorEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.ejb.NoSuchEntityException;
import java.util.List;


@Service
public class FacultyServices {

    @Autowired
    private FacultyDaoImpl facultyDao;

    @Autowired
    private ValidatorEntity<Faculty> validator;
    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(FacultyServices.class);
    private static final String MISSING_ID = "Missing id faculty.";

    public List<Faculty> getAll() {
        logger.debug("Trying to get all faculties");

        try {
            return facultyDao.getAll();
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Faculties is not exist");
            throw new NoSuchEntityException("Doesn't exist such faculties");
        } catch (DataAccessException e) {
            logger.error("Failed to get all faculties", e);
            throw new ServiceException("Failed to get list of faculties", e);
        }
    }

    public boolean create(Faculty faculty) {
        logger.debug("Trying to create faculty: {}", faculty);

        validator.validate(faculty);
        try {
            return facultyDao.create(faculty);
        } catch (DataAccessException e) {
            logger.error("failed to create faculty: {}", faculty, e);
            throw new ServiceException("Failed to create new faculty", e);
        }
    }

    public boolean delete(long id) {
        logger.debug("Trying to delete faculty with id={}", id);

        if (id == 0) {
            logger.warn(MISSING_ID);
            throw new ServiceException(MISSING_ID);
        }
        try {
            return facultyDao.delete(id);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Is not existing faculty with id={}", id);
            throw new NoSuchEntityException("Doesn't exist such faculty");
        } catch (DataAccessException e) {
            logger.error("failed to delete faculty with id={}", id, e);
            throw new ServiceException("Failed to delete faculty by id", e);
        }
    }

    public Faculty getById(long id) {
        logger.debug("Trying to get faculty with id={}", id);

        if (id == 0) {
            logger.warn(MISSING_ID);
            throw new ServiceException(MISSING_ID);
        }
        Faculty faculty;
        try {
            faculty = facultyDao.getById(id);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Is not existing faculty with id={}", id);
            throw new NoSuchEntityException("Doesn't exist such faculty");
        } catch (DataAccessException e) {
            logger.error("Failed to retrieve faculty with id={}", id, e);
            throw new ServiceException("Failed to retrieve faculty with such id", e);
        }
        return faculty;
    }

    public boolean update(Faculty faculty) {
        logger.debug("Trying to update faculty: {}", faculty);

        if (faculty.getFacultyId() == 0) {
            logger.warn(MISSING_ID);
            throw new ServiceException(MISSING_ID);
        }
        validator.validate(faculty);
        try {
            facultyDao.getById(faculty.getFacultyId());
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Is not existing faculty: {}", faculty);
            throw new NoSuchEntityException("Doesn't exist such faculty");
        } catch (DataAccessException e) {
            logger.error("Failed to retrieve faculty: {}", faculty);
            throw new ServiceException("Failed to retrieve faculty: ", e);
        }
        try {
            return facultyDao.update(faculty);
        } catch (DataAccessException e) {
            logger.error("Failed to update faculty: {}", faculty);
            throw new ServiceException("Problem with updating faculty");
        }
    }
}


