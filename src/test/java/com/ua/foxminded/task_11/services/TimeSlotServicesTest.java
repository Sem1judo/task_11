package com.ua.foxminded.task_11.services;

import com.ua.foxminded.task_11.dao.impl.TimeSlotDaoImpl;
import com.ua.foxminded.task_11.exceptions.ServiceException;
import com.ua.foxminded.task_11.model.Group;
import com.ua.foxminded.task_11.model.Lector;
import com.ua.foxminded.task_11.model.TimeSlot;
import com.ua.foxminded.task_11.validation.ValidatorEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class TimeSlotServicesTest {

    @Mock
    private TimeSlotDaoImpl timeSlotDao;

    @InjectMocks
    private TimeSlotServices timeSlotServices;

    @InjectMocks
    private ValidatorEntity<TimeSlot> validator;

    private LocalDateTime start;
    private LocalDateTime end;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(timeSlotServices, "validator", validator);
        start = LocalDateTime.of(2021, 12, 12, 12, 12);
        end = LocalDateTime.of(2021, 12, 12, 14, 12);

    }

    @Test
    public void shouldGetAllTimeSlots() {

        List<TimeSlot> list = new ArrayList<>();
        TimeSlot tOne = new TimeSlot(1, start, end, new Group(), new Lector());
        TimeSlot tTwo = new TimeSlot(1, start, end, new Group(), new Lector());

        TimeSlot tThree = new TimeSlot(1, start, end, new Group(), new Lector());

        list.add(tOne);
        list.add(tTwo);
        list.add(tThree);

        when(timeSlotDao.getAll()).thenReturn(list);

        List<TimeSlot> timeSlots = timeSlotServices.getAll();

        assertEquals(3, timeSlots.size());
        verify(timeSlotDao, times(1)).getAll();
    }

    @Test
    public void shouldGetByIdTimeSlot() {
        when(timeSlotDao.getById(1L)).thenReturn(new TimeSlot(1, start, end, new Group(), new Lector()));

        TimeSlot timeSlot = timeSlotServices.getById(1L);

        assertEquals(start.toString(), timeSlot.getStartLesson().toString());
        assertEquals(end.toString(), timeSlot.getEndLesson().toString());
        assertEquals(1, timeSlot.getTimeSlotId());
    }

    @Test
    public void shouldCreateTimeSlot() {
        TimeSlot timeSlot = new TimeSlot(1, start, end, new Group(), new Lector());

        when(timeSlotDao.create(eq(timeSlot))).thenReturn(Boolean.TRUE);
        boolean isCreated = timeSlotServices.create(timeSlot);

        verify(timeSlotDao, times(1)).create(timeSlot);
        assertTrue(isCreated);
    }

    @Test
    public void shouldDeleteTimeSlot() {
        timeSlotServices.delete(1L);
        verify(timeSlotDao, times(1)).delete(1L);
    }

    @Test
    public void shouldUpdateTimeSlot() {
        TimeSlot timeSlot = new TimeSlot(1, start, end, new Group(), new Lector());

        when(timeSlotDao.update(eq(timeSlot))).thenReturn(Boolean.TRUE);

        boolean isUpdated = timeSlotServices.update(timeSlot);

        verify(timeSlotDao, times(1)).update(timeSlot);
        assertTrue(isUpdated);
    }

    @Test
    public void shouldOutputAppropriateSentencesWhenDateIsNull() {
        TimeSlot timeSlot = new TimeSlot(1L, null,
                null, new Group(), new Lector());

        assertThrows(ServiceException.class, () -> validator.validate(timeSlot));
    }

    @Test
    public void shouldOutputAppropriateSentencesWhenDateIsPassed() {
        TimeSlot timeSlot = new TimeSlot(1L, LocalDateTime.of(2019, 6, 10, 10, 15),
                LocalDateTime.of(2019, 6, 10, 12, 15), new Group(), new Lector());

        assertThrows(ServiceException.class, () -> validator.validate(timeSlot));
    }

    @Test
    public void shouldPassWhenValid() {
        TimeSlot timeSlot = new TimeSlot(1L, LocalDateTime.of(2021, 6, 10, 10, 15),
                LocalDateTime.of(2021, 6, 10, 12, 15), new Group(), new Lector());

        assertDoesNotThrow(() -> validator.validate(timeSlot));
        assertEquals("2021-06-10T10:15", timeSlot.getStartLesson().toString());
        assertEquals("2021-06-10T12:15", timeSlot.getEndLesson().toString());
    }

}

