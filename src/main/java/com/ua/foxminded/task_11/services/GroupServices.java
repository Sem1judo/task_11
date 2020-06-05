package com.ua.foxminded.task_11.services;

import com.ua.foxminded.task_11.dao.impl.GroupDaoImpl;
import com.ua.foxminded.task_11.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Component
public class GroupServices {
    @Autowired
    private GroupDaoImpl groupDao;

    public Optional<List<Group>> getGroups() {
        return Optional.ofNullable(groupDao.getAll());
    }

    public void createNewGroup(@Valid String nameGroup) {
        Group group = new Group();
        group.setName(nameGroup);
        groupDao.create(group);
    }

    public void deleteGroup(long id) {
        groupDao.delete(id);
    }

    public Optional<Group> getGroup(long id) {
        return Optional.ofNullable(groupDao.getById(id));
    }

    public void updateGroup(long id,@Valid String nameGroup) {
        Group group = groupDao.getById(id);
        group.setName(nameGroup);
        groupDao.update(group);
    }

    public Optional<Integer> getLessonsForGroup(Long id) {
        return Optional.of(groupDao.getLessonsById(id));
    }
}
