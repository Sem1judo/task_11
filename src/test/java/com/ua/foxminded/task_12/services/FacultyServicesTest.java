package com.ua.foxminded.task_12.services;

import com.ua.foxminded.task_12.dao.impl.FacultyDaoImpl;
import com.ua.foxminded.task_12.exceptions.ServiceException;
import com.ua.foxminded.task_12.model.Faculty;
import com.ua.foxminded.task_12.validation.ValidatorEntity;
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
        List<Faculty> initialFaculties = new ArrayList<>();
        Faculty testFaculty1 = new Faculty(1, "Biology", new ArrayList<>(), new ArrayList<>());
        Faculty testFaculty2 = new Faculty(2, "Math", new ArrayList<>(), new ArrayList<>());
        Faculty testFaculty3 = new Faculty(3, "Technology", new ArrayList<>(), new ArrayList<>());

        initialFaculties.add(testFaculty1);
        initialFaculties.add(testFaculty2);
        initialFaculties.add(testFaculty3);

        when(facultyDao.getAll()).thenReturn(initialFaculties);

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

        boolean isCreated = facultyServices.create(faculty);

        verify(facultyDao, times(1)).create(faculty);
        assertTrue(isCreated);
    }

    @Test
    public void shouldDeleteFaculty() {

        when(facultyDao.delete(eq(1L))).thenReturn(Boolean.TRUE);
        boolean isDeleted = facultyServices.delete(1L);

        assertTrue(isDeleted);
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
    public void shouldThrowServiceExceptionWhenNameIsNull() {
        Faculty faculty = new Faculty(1, null, new ArrayList<>(), new ArrayList<>());
        assertThrows(ServiceException.class, () -> facultyServices.create(faculty));
    }

    @Test
    public void shouldThrowServiceExceptionWhenNameTooShort() {
        Faculty faculty = new Faculty(1, "D", new ArrayList<>(), new ArrayList<>());
        assertThrows(ServiceException.class, () -> facultyServices.create(faculty));
    }

    @Test
    public void shouldThrowServiceExceptionWhenIdZero() {
        Faculty faculty = new Faculty(0, "Normal", new ArrayList<>(), new ArrayList<>());
        assertThrows(ServiceException.class, () -> facultyServices.update(faculty));
    }

    @Test
    public void shouldThrowServiceExceptionWhenNameHaveForbiddenSymbol() {
        Faculty faculty = new Faculty(1, "Nam_e"
                , new ArrayList<>(), new ArrayList<>());
        assertThrows(ServiceException.class, () -> facultyServices.update(faculty));
    }

    @Test
    public void shouldThrowServiceExceptionWhenNameIsTooLong() {
        Faculty faculty = new Faculty(1, "verybignamewhichcantbethereaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                , new ArrayList<>(), new ArrayList<>());
        assertThrows(ServiceException.class, () -> facultyServices.update(faculty));
    }

}

