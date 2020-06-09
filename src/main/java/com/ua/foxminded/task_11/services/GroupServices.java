package com.ua.foxminded.task_11.services;

import com.ua.foxminded.task_11.dao.impl.GroupDaoImpl;
import com.ua.foxminded.task_11.exceptions.DaoException;
import com.ua.foxminded.task_11.exceptions.ServicesException;
import com.ua.foxminded.task_11.model.Faculty;
import com.ua.foxminded.task_11.model.Group;
import com.ua.foxminded.task_11.validation.ValidatorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.validation.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class GroupServices {
    @Autowired
    private GroupDaoImpl groupDao;

    @Autowired
    private ValidatorEntity validator;

    public List<Group> getGroups() throws ServicesException {
        try {
            return groupDao.getAll();
        } catch (DaoException e) {
            throw new ServicesException("Failed to get groups", e);
        }
    }

    public boolean createNewGroup(@Valid Group group) throws ServicesException {
        try {
            return groupDao.create(group);
        } catch (DaoException e) {
            throw new ServicesException("Failed to create group", e);
        }
    }

    public boolean deleteGroup(long id) throws ServicesException {
        try {
            return groupDao.delete(id);
        } catch (DaoException e) {
            throw new ServicesException("Failed to delete group by id", e);
        }
    }

    public Group getGroup(long id) throws ServicesException {
        Group group;
        try {
            group = groupDao.getById(id);
        } catch (DaoException e) {
            throw new ServicesException("Failed to retrieve group by id", e);
        }
        return group;
    }

    public boolean updateGroup(@Valid Group group) throws ServicesException {
        if (group.getGroupId() == 0) {
            throw new ServicesException("Missing id");
        }

        Set<ConstraintViolation<Group>> constraintViolations = validator.getValidatorInstance().validate(group);
        if (!constraintViolations.isEmpty()) {
            throw new ServicesException("Data is not valid: " + constraintViolations.iterator().next());
        }

        try {
            groupDao.getById(group.getGroupId());
        } catch (DaoException e) {
            throw new ServicesException("Failed to retrieve faculty from id", e);
        }

        try {
            return groupDao.update(group);
        } catch (DaoException e) {
            throw new ServicesException("Problem with updating faculty");
        }
    }

    public int getLessonsForGroup(Long id) throws ServicesException {
        try {
            return groupDao.getLessonsById(id);
        } catch (DaoException e) {
            throw new ServicesException("Failed to get lessons group by id", e);
        }

    }
}
