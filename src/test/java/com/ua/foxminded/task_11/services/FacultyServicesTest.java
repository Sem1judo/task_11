package com.ua.foxminded.task_11.services;

import com.ua.foxminded.task_11.dao.impl.FacultyDaoImpl;
import com.ua.foxminded.task_11.exceptions.ServiceException;
import com.ua.foxminded.task_11.model.Faculty;
import com.ua.foxminded.task_11.validation.ValidatorEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FacultyServicesTest {

    @Mock
    private FacultyDaoImpl facultyDao;

    @InjectMocks
    private ValidatorEntity<Faculty> validator;

    @InjectMocks
    private FacultyServices facultyServices;


    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(facultyServices, "validator", validator);
    }


    @Test
    public void shouldGetAllFaculties() {
        List<Faculty> list = new ArrayList<>();
        Faculty fOne = new Faculty(1, "Biology", new ArrayList<>(), new ArrayList<>());
        Faculty fTwo = new Faculty(2, "Math", new ArrayList<>(), new ArrayList<>());
        Faculty fThree = new Faculty(3, "Technology", new ArrayList<>(), new ArrayList<>());

        list.add(fOne);
        list.add(fTwo);
        list.add(fThree);

        when(facultyDao.getAll()).thenReturn(list);

        List<Faculty> faculties = facultyServices.getAll();

        assertEquals(3, faculties.size());
        verify(facultyDao, times(1)).getAll();
    }

    @Test
    public void shouldGetByIdFaculty() {
        when(facultyDao.getById(1L)).thenReturn(new Faculty(1, "Biology", new ArrayList<>(), new ArrayList<>()));

        Faculty faculty = facultyServices.getById(1L);

        assertEquals("Biology", faculty.getName());
        assertEquals(1, faculty.getFacultyId());
    }

    @Test
    public void shouldCreateFaculty() {
        Faculty faculty = new Faculty(1, "Biology", new ArrayList<>(), new ArrayList<>());

        when(facultyDao.create(eq(faculty))).thenReturn(Boolean.TRUE);

        System.out.println(faculty);
        boolean isCreated = facultyServices.create(faculty);
        System.out.println(faculty);

        verify(facultyDao, times(1)).create(faculty);
        assertTrue(isCreated);
    }

    @Test
    public void shouldDeleteFaculty() {
        facultyServices.delete(1L);
        verify(facultyDao, times(1)).delete(1L);
    }

    @Test
    public void shouldUpdateFaculty() {
        Faculty faculty = new Faculty(1, "Math", new ArrayList<>(), new ArrayList<>());

        when(facultyDao.update(eq(faculty))).thenReturn(Boolean.TRUE);

        boolean isCreated = facultyServices.update(faculty);

        verify(facultyDao, times(1)).update(faculty);
        assertTrue(isCreated);
    }

    @Test
    public void shouldOutputExceptionWhenNameIsNull() {
        Faculty faculty = new Faculty(1, null, new ArrayList<>(), new ArrayList<>());

        assertThrows(ServiceException.class, () -> validator.validate(faculty));
    }

    @Test
    public void shouldOutputAppropriateSentencesWhenNameTooShort() {
        Faculty faculty = new Faculty(1, "D", new ArrayList<>(), new ArrayList<>());

        assertThrows(ServiceException.class, () -> validator.validate(faculty));
    }

    @Test
    public void shouldPassWhenValid() {
        Faculty faculty = new Faculty(1, "Biology", new ArrayList<>(), new ArrayList<>());

        assertDoesNotThrow(() -> validator.validate(faculty));
        assertEquals("Biology", faculty.getName());
    }
}

