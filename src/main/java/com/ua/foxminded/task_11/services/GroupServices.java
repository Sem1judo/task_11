package com.ua.foxminded.task_11.services;

import com.ua.foxminded.task_11.dao.impl.GroupDaoImpl;
import com.ua.foxminded.task_11.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class GroupServices {
    @Autowired
    private GroupDaoImpl groupDao;

    public List<Group> getGroups() {
        return groupDao.getAll();
    }

    public void createNewGroup(String nameGroup) {
        Group group = new Group();
        group.setName(nameGroup);
        groupDao.create(group);
    }

    public void deleteGroup(long id) {
        groupDao.delete(id);
    }

    public Group getGroup(long id) {
        return groupDao.getById(id);
    }

    public void updateGroup(long id, String nameGroup) {
        Group group = groupDao.getById(id);
        group.setName(nameGroup);
        groupDao.update(group);
    }

    public int getLessonsForGroup(Long id) {
        return groupDao.getLessonsById(id);
    }
}
