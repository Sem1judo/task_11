package com.ua.foxminded.task_12.services;

import com.ua.foxminded.task_12.dao.impl.GroupDaoImpl;
import com.ua.foxminded.task_12.exceptions.ServiceException;
import com.ua.foxminded.task_12.model.Group;
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
public class GroupServices {
    @Autowired
    private GroupDaoImpl groupDao;

    @Autowired
    private ValidatorEntity<Group> validator;

    private static final Logger logger = LoggerFactory.getLogger(GroupServices.class);

    private static final String MISSING_ID = "Missing id group.";
    private static final String NOT_EXIST_ENTITY = "Doesn't exist such group";


    public List<Group> getAll() {
        logger.debug("Trying to get all groups");

        try {
            return groupDao.getAll();
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Groups is not exist");
            throw new NoSuchEntityException("Doesn't exist such groups");
        } catch (DataAccessException e) {
            logger.error("Failed to get all groups", e);
            throw new ServiceException("Failed to get list of groups", e);
        }
    }

    public boolean create(Group group) {
        logger.debug("Trying to create group: {}", group);

        validator.validate(group);
        try {
            return groupDao.create(group);
        } catch (DataAccessException e) {
            logger.error("Failed to create group: {}", group, e);
            throw new ServiceException("Failed to create group", e);
        }
    }

    public boolean delete(long id) {
        logger.debug("Trying to delete group with id={}", id);

        if (id == 0) {
            logger.warn(MISSING_ID);
            throw new ServiceException(MISSING_ID);
        }
        try {
            return groupDao.delete(id);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Not existing group with id={}", id);
            throw new NoSuchEntityException(NOT_EXIST_ENTITY);
        } catch (DataAccessException e) {
            logger.error("Failed to delete group with id={}", id, e);
            throw new ServiceException("Failed to delete group by id", e);
        }
    }

    public Group getById(long id) {
        logger.debug("Trying to get group with id={}", id);

        if (id == 0) {
            logger.warn(MISSING_ID);
            throw new ServiceException(MISSING_ID);
        }
        Group group;
        try {
            group = groupDao.getById(id);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Not existing group with id={}", id);
            throw new NoSuchEntityException(NOT_EXIST_ENTITY);
        } catch (DataAccessException e) {
            logger.error("Failed to retrieve group with id={}", id, e);
            throw new ServiceException("Failed to retrieve group by id", e);
        }
        return group;
    }

    public boolean update(Group group) {
        logger.debug("Trying to update group: {}", group);

        if (group.getGroupId() == 0) {
            logger.warn(MISSING_ID);
            throw new ServiceException(MISSING_ID);
        }
        validator.validate(group);
        try {
            groupDao.getById(group.getGroupId());
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Not existing group: {}", group);
            throw new NoSuchEntityException(NOT_EXIST_ENTITY);
        } catch (DataAccessException e) {
            logger.error("Failed to retrieve group: {}", group);
            throw new ServiceException("Failed to retrieve group from such id: ", e);
        }

        try {
            return groupDao.update(group);
        } catch (DataAccessException e) {
            logger.error("Failed to update group: {}", group);
            throw new ServiceException("Problem with updating group");
        }
    }

    public int getLessonsForGroup(Long id) {
        logger.debug("Trying to get lessons for group with id={}", id);
        if (id == 0) {
            logger.warn(MISSING_ID);
            throw new ServiceException(MISSING_ID);
        }
        try {
            return groupDao.getLessonsById(id);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Not existing group with id={}", id);
            throw new NoSuchEntityException("Doesn't exist such lessons for group");
        } catch (DataAccessException e) {
            logger.error("Failed to get lessons group by such id={}", id);
            throw new ServiceException("Failed to get lessons group by such id", e);
        }

    }
}
