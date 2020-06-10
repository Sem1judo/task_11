package com.ua.foxminded.task_11.services;

import com.ua.foxminded.task_11.dao.impl.GroupDaoImpl;
import com.ua.foxminded.task_11.exceptions.ServicesException;
import com.ua.foxminded.task_11.model.Group;
import com.ua.foxminded.task_11.validation.ValidatorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;


import javax.validation.*;
import java.util.List;
import java.util.Set;

@Service
public class GroupServices {
    @Autowired
    private GroupDaoImpl groupDao;

    @Autowired
    private ValidatorEntity validator;

    public List<Group> getGroups()  {
        try {
            return groupDao.getAll();
        } catch (DataAccessException e) {
            throw new ServicesException("Failed to get groups", e);
        }
    }

    public boolean createNewGroup(@Valid Group group) {
        try {
            return groupDao.create(group);
        } catch (DataAccessException e) {
            throw new ServicesException("Failed to create group", e);
        }
    }

    public boolean deleteGroup(long id) {
        if (id == 0) {
            throw new ServicesException("Missing id");
        }
        try {
            return groupDao.delete(id);
        } catch (DataAccessException e) {
            throw new ServicesException("Failed to delete group by id", e);
        }
    }

    public Group getGroup(long id) {
        if (id == 0) {
            throw new ServicesException("Missing id");
        }
        Group group;
        try {
            group = groupDao.getById(id);
        } catch (DataAccessException e){
            throw new ServicesException("Failed to retrieve group by id", e);
        }
        return group;
    }

    public boolean updateGroup(@Valid Group group) {
        if (group.getGroupId() == 0) {
            throw new ServicesException("Missing id");
        }

        Set<ConstraintViolation<Group>> constraintViolations = validator.getValidatorInstance().validate(group);
        if (!constraintViolations.isEmpty()) {
            throw new ServicesException("Data is not valid: " + constraintViolations.iterator().next());
        }

        try {
            groupDao.getById(group.getGroupId());
        } catch (DataAccessException e){
            throw new ServicesException("Failed to retrieve faculty from such id: ", e);
        }

        try {
            return groupDao.update(group);
        } catch (DataAccessException e) {
            throw new ServicesException("Problem with updating faculty");
        }
    }

    public int getLessonsForGroup(Long id) {
        if (id == 0) {
            throw new ServicesException("Missing id");
        }
        try {
            return groupDao.getLessonsById(id);
        } catch (DataAccessException e) {
            throw new ServicesException("Failed to get lessons group by such id", e);
        }

    }
}
