package com.ua.foxminded.task_11.services;

import com.sun.tools.internal.ws.wsdl.framework.NoSuchEntityException;
import com.ua.foxminded.task_11.dao.impl.GroupDaoImpl;
import com.ua.foxminded.task_11.exceptions.ServiceException;
import com.ua.foxminded.task_11.model.Faculty;
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
    private ValidatorEntity<Group> validator;

    public List<Group> getAll() {
        try {
            return groupDao.getAll();
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchEntityException("Doesn't exist such groups");
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to get groups", e);
        }
    }

    public boolean create(@Valid Group group) {
        try {
            validator.validate(group);
        } catch (ServiceException e) {
            throw new ServiceException("Don't pass validation");
        }
        try {
            return groupDao.create(group);
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to create group", e);
        }
    }

    public boolean delete(long id) {
        if (id == 0) {
            throw new ServiceException("Missing id");
        }
        try {
            return groupDao.delete(id);
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to delete group by id", e);
        }
    }

    public Group getById(long id) {
        if (id == 0) {
            throw new ServiceException("Missing id");
        }
        Group group;
        try {
            group = groupDao.getById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchEntityException("Doesn't exist such group");
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to retrieve group by id", e);
        }
        return group;
    }

    public boolean update(@Valid Group group) {
        if (group.getGroupId() == 0) {
            throw new ServiceException("Missing id");
        }
        try {
            validator.validate(group);
        } catch (ServiceException e) {
            throw new ServiceException("Don't pass validation");
        }
        try {
            groupDao.getById(group.getGroupId());
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to retrieve faculty from such id: ", e);
        }

        try {
            return groupDao.update(group);
        } catch (DataAccessException e) {
            throw new ServiceException("Problem with updating faculty");
        }
    }

    public int getLessonsForGroup(Long id) {
        if (id == 0) {
            throw new ServiceException("Missing id");
        }
        try {
            return groupDao.getLessonsById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchEntityException("Doesn't exist such lessons for group");
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to get lessons group by such id", e);
        }

    }
}
