package com.ua.foxminded.task_11.services;

import com.ua.foxminded.task_11.dao.impl.LectorDaoImpl;
import com.ua.foxminded.task_11.exceptions.ServiceException;
import com.ua.foxminded.task_11.model.Group;
import com.ua.foxminded.task_11.model.Lector;
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

class LectorServicesTest {
    @Mock
    private LectorDaoImpl lectorDao;

    @InjectMocks
    private ValidatorEntity<Lector> validator;

    @InjectMocks
    private LectorServices lectorServices;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(lectorServices, "validator", validator);
    }

    @Test
    public void shouldGetAllLectors() {
        List<Lector> list = new ArrayList<>();
        Lector lOne = new Lector(1L, 1L, "Andrey", "Borisov");
        Lector lTwo = new Lector(2L, 2L, "Boris", "Andeyes");
        Lector lThree = new Lector(2L, 2L, "Andress", "Larensov");

        list.add(lOne);
        list.add(lTwo);
        list.add(lThree);

        when(lectorDao.getAll()).thenReturn(list);

        List<Lector> lectors = lectorServices.getAll();

        assertEquals(3, lectors.size());
        verify(lectorDao, times(1)).getAll();
    }

    @Test
    public void shouldGetByIdLector() {
        when(lectorDao.getById(1L)).thenReturn(new Lector(1L, 1L, "Andrey", "Borisov"));

        Lector lector = lectorServices.getById(1L);

        assertEquals("Andrey", lector.getFirstName());
        assertEquals("Borisov", lector.getLastName());
        assertEquals(1, lector.getLectorId());
    }

    @Test
    public void shouldCreateLector() {
        Lector lector = (new Lector(1L, 1L, "Andrey", "Borisov"));

        when(lectorDao.create(eq(lector))).thenReturn(Boolean.TRUE);

        boolean isCreated = lectorServices.create(lector);

        verify(lectorDao, times(1)).create(lector);
        assertTrue(isCreated);
    }

    @Test
    public void shouldDeleteLector() {
        lectorServices.delete(1L);
        verify(lectorDao, times(1)).delete(1L);
    }

    @Test
    public void shouldUpdateLector() {
        Lector lector = (new Lector(1L, 1L, "Andrey", "Borisov"));
        when(lectorDao.update(eq(lector))).thenReturn(Boolean.TRUE);
       boolean isUpdated =  lectorServices.update(lector);

        verify(lectorDao, times(1)).update(lector);
        assertTrue(isUpdated);
    }

    @Test
    public void shouldOutputAppropriateSentencesWhenNameIsNull() {
        Lector lector = new Lector(1L, null, null);

        assertThrows(ServiceException.class, () -> validator.validate(lector));
    }

    @Test
    public void shouldOutputAppropriateSentencesWhenNameOrSurnameTooShort() {
        Lector lector = new Lector(1L, "V", "V");

        assertThrows(ServiceException.class, () -> validator.validate(lector));
    }

    @Test
    public void shouldPassWhenValid() {

        Lector lector = new Lector(1L, "ValidName", "ValidSurname");

        assertDoesNotThrow(() -> validator.validate(lector));
        assertEquals("ValidName", lector.getFirstName());
        assertEquals("ValidSurname", lector.getLastName());
    }
}