package com.ua.foxminded.task_11.services;

import com.ua.foxminded.task_11.dao.impl.GroupDaoImpl;
import com.ua.foxminded.task_11.model.Group;


import java.util.List;

public class GroupServices {
    private GroupDaoImpl groupDao = new GroupDaoImpl();

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
