package com.ua.foxminded.task_11.services;

import com.ua.foxminded.task_11.dao.impl.FacultyDaoImpl;
import com.ua.foxminded.task_11.model.Faculty;
import com.ua.foxminded.task_11.validation.ValidatorEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


class FacultyServicesTest {

    @Mock
    private FacultyDaoImpl facultyDao;

    @InjectMocks
    private FacultyServices facultyServices;

    @InjectMocks
    private ValidatorEntity validator;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void shouldGetAll() {
        List<Faculty> list = new ArrayList<>();
        Faculty fOne = new Faculty(1, "Biology", new ArrayList<>(), new ArrayList<>());
        Faculty fTwo = new Faculty(2, "Math", new ArrayList<>(), new ArrayList<>());
        Faculty fThree = new Faculty(3, "Technology", new ArrayList<>(), new ArrayList<>());

        list.add(fOne);
        list.add(fTwo);
        list.add(fThree);

        when(facultyDao.getAll()).thenReturn(list);

        List<Faculty> empList = facultyServices.getAll();

        assertEquals(3, empList.size());
        verify(facultyDao, times(1)).getAll();
    }

    @Test
    public void shouldGetById() {
        when(facultyDao.getById(1L)).thenReturn(new Faculty(1, "Biology", new ArrayList<>(), new ArrayList<>()));

        Faculty faculty = facultyServices.getById(1L);

        assertEquals("Biology", faculty.getName());
        assertEquals(1, faculty.getFacultyId());
    }

    @Test
    public void shouldCreate() {
        Faculty faculty = new Faculty(1, "Biology", new ArrayList<>(), new ArrayList<>());

        boolean isCreated = facultyServices.create(faculty);

        verify(facultyDao, times(1)).create(faculty);
        assertTrue(isCreated);
    }

    @Test
    public void shouldDelete() {
        facultyServices.delete(1L);
        verify(facultyDao, times(1)).delete(1L);
    }

    @Test
    public void shouldUpdate() {
        Faculty faculty = new Faculty(1, "Math", new ArrayList<>(), new ArrayList<>());
        facultyServices.update(faculty);
        verify(facultyDao, times(1)).update(faculty);
    }

}

