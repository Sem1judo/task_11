package com.ua.foxminded.task_11.services;

import com.ua.foxminded.task_11.dao.impl.GroupDaoImpl;
import com.ua.foxminded.task_11.exceptions.ServiceException;
import com.ua.foxminded.task_11.model.Group;
import com.ua.foxminded.task_11.validation.ValidatorEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class GroupServicesTest {
    @Mock
    private GroupDaoImpl groupDao;

    @InjectMocks
    private ValidatorEntity<Group> validator;

    @InjectMocks
    private GroupServices groupServices;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(groupServices, "validator", validator);

    }

    @Test
    public void shouldGetAllGroups() {
        List<Group> list = new ArrayList<>();
        Group gOne = new Group(1, 1, "Fb-12");
        Group gTwo = new Group(2, 2, "IT-22");
        Group gThree = new Group(3, 3, "N1-11");

        list.add(gOne);
        list.add(gTwo);
        list.add(gThree);

        when(groupDao.getAll()).thenReturn(list);

        List<Group> groups = groupServices.getAll();

        assertEquals(3, groups.size());
        verify(groupDao, times(1)).getAll();
    }

    @Test
    public void shouldGetByIdGroup() {
        when(groupDao.getById(1L)).thenReturn(new Group(1, 1, "Fb-12"));

        Group group = groupServices.getById(1L);

        assertEquals("Fb-12", group.getName());
        assertEquals(1, group.getGroupId());
    }

    @Test
    public void shouldCreateGroup() {
        Group group = new Group(1, 1, "Fb-12");

        when(groupDao.create(eq(group))).thenReturn(Boolean.TRUE);

        boolean isCreated = groupServices.create(group);

        verify(groupDao, times(1)).create(group);
        assertTrue(isCreated);
    }

    @Test
    public void shouldDeleteGroup() {
        when(groupDao.delete(eq(1L))).thenReturn(Boolean.TRUE);

        boolean isDeleted = groupServices.delete(1L);

        assertTrue(isDeleted);
        verify(groupDao, times(1)).delete(1L);
    }

    @Test
    public void shouldUpdateGroup() {
        Group group = new Group(1, 1, "Fb-12");

        when(groupDao.update(eq(group))).thenReturn(Boolean.TRUE);

        boolean isCreated = groupServices.update(group);

        verify(groupDao, times(1)).update(group);
        assertTrue(isCreated);
    }

    @Test
    public void shouldThrowServiceExceptionWhenNameIsNull() {
        Group group = new Group(1, 1, null);
        assertThrows(ServiceException.class, () -> groupServices.create(group));
    }

    @Test
    public void shouldThrowServiceExceptionWhenNameTooShort() {
        Group group = new Group(1, 1, "Fb");
        assertThrows(ServiceException.class, () -> groupServices.create(group));
    }

    @Test
    public void shouldThrowServiceExceptionWhenIdZero() {
        Group group = new Group(0, 1, "Fb-12");
        assertThrows(ServiceException.class, () -> groupServices.update(group));
    }

    @Test
    public void shouldThrowServiceExceptionWhenNameHaveForbiddenSymbol() {
        Group group = new Group(1, 1, "Fb_12");
        assertThrows(ServiceException.class, () -> groupServices.create(group));
    }

    @Test
    public void shouldThrowServiceExceptionWhenNameIsTooLong() {
        Group group = new Group(1, 1, "aaaaaaaaaaFbaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        assertThrows(ServiceException.class, () -> groupServices.create(group));
    }

}

