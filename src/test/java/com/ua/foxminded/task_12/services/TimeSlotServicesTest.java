package com.ua.foxminded.task_12.services;

import com.ua.foxminded.task_12.dao.impl.TimeSlotDaoImpl;
import com.ua.foxminded.task_12.exceptions.ServiceException;

import com.ua.foxminded.task_12.model.Group;
import com.ua.foxminded.task_12.model.Lector;
import com.ua.foxminded.task_12.model.TimeSlot;
import com.ua.foxminded.task_12.validation.ValidatorEntity;
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

        List<TimeSlot> initialTimeSlots = new ArrayList<>();
        TimeSlot testTimeSLot1 = new TimeSlot(1, start, end, new Group(), new Lector());
        TimeSlot testTimeSLot2 = new TimeSlot(1, start, end, new Group(), new Lector());
        TimeSlot testTimeSLot3 = new TimeSlot(1, start, end, new Group(), new Lector());

        initialTimeSlots.add(testTimeSLot1);
        initialTimeSlots.add(testTimeSLot2);
        initialTimeSlots.add(testTimeSLot3);

        when(timeSlotDao.getAll()).thenReturn(initialTimeSlots);

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
        when(timeSlotDao.delete(1L)).thenReturn(Boolean.TRUE);

        boolean isDeleted = timeSlotServices.delete(1L);

        assertTrue(isDeleted);
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
    public void shouldPassWhenFacultyDateValid() {
        TimeSlot timeSlot = new TimeSlot(1L, LocalDateTime.of(2021, 6, 10, 10, 15),
                LocalDateTime.of(2021, 6, 10, 12, 15), new Group(), new Lector());

        assertDoesNotThrow(() -> validator.validate(timeSlot));
        assertEquals("2021-06-10T10:15", timeSlot.getStartLesson().toString());
        assertEquals("2021-06-10T12:15", timeSlot.getEndLesson().toString());
    }


    @Test
    public void shouldThrowServiceExceptionWhenStartDateIsNull() {
        TimeSlot timeSlot = new TimeSlot(1L, null,
                LocalDateTime.of(2021, 6, 10, 12, 15), new Group(), new Lector());

        assertThrows(ServiceException.class, () -> timeSlotServices.create(timeSlot));
    }

    @Test
    public void shouldThrowServiceExceptionWhenEndDateIsNull() {
        TimeSlot timeSlot = new TimeSlot(1L, LocalDateTime.of(2021, 6, 10, 12, 15),
                null, new Group(), new Lector());

        assertThrows(ServiceException.class, () -> timeSlotServices.create(timeSlot));
    }

    @Test
    public void shouldThrowServiceExceptionWhenStartDateIsAlreadyPass() {
        TimeSlot timeSlot = new TimeSlot(1L, LocalDateTime.of(2019, 6, 10, 10, 15),
                LocalDateTime.of(2029, 6, 10, 12, 15), new Group(), new Lector());

        assertThrows(ServiceException.class, () -> timeSlotServices.create(timeSlot));
    }

    @Test
    public void shouldThrowServiceExceptionWhenEndDateIsAlreadyPass() {
        TimeSlot timeSlot = new TimeSlot(1L, LocalDateTime.of(2021, 6, 10, 10, 15),
                LocalDateTime.of(2015, 6, 10, 12, 15), new Group(), new Lector());

        assertThrows(ServiceException.class, () -> timeSlotServices.create(timeSlot));
    }

    @Test
    public void shouldThrowServiceExceptionWhenIdZero() {
        TimeSlot timeSlot = new TimeSlot(0L, LocalDateTime.of(2021, 6, 10, 10, 15),
                LocalDateTime.of(2015, 6, 10, 12, 15), new Group(), new Lector());

        assertThrows(ServiceException.class, () -> timeSlotServices.update(timeSlot));
    }
}

