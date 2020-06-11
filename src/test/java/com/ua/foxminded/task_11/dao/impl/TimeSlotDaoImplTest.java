package com.ua.foxminded.task_11.dao.impl;

import com.ua.foxminded.task_11.model.Group;
import com.ua.foxminded.task_11.model.Lector;
import com.ua.foxminded.task_11.model.Lesson;
import com.ua.foxminded.task_11.model.TimeSlot;
import com.ua.foxminded.task_11.model.mapper.TimeSLotMapper;
import com.ua.foxminded.task_11.validation.ValidatorEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import javax.validation.ConstraintViolation;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class TimeSlotDaoImplTest {

    @Mock
    JdbcTemplate jdbcTemplate;
    @InjectMocks
    TimeSlotDaoImpl timeSlotDao;
    @InjectMocks
    ValidatorEntity validator;


    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(timeSlotDao, "jdbcTemplate", jdbcTemplate);
    }

    @Test
    public void shouldCreateTimeSlot() {
        when(jdbcTemplate.update(eq("insert into time_slots(start_lesson, end_lesson) values(?,?)")
                , eq(LocalDateTime.parse("2020-01-01T12:00:00")), eq(LocalDateTime.parse("2020-01-01T14:00:00"))))
                .thenReturn(1);

        TimeSlot timeSlot = new TimeSlot();

        timeSlot.setStartLesson(LocalDateTime.parse("2020-01-01T12:00:00"));
        timeSlot.setEndLesson(LocalDateTime.parse("2020-01-01T14:00:00"));

        boolean isCreated = timeSlotDao.create(timeSlot);
        assertTrue(isCreated);
    }

    @Test
    public void shouldUpdatedTimeSlot() {
        when(jdbcTemplate.update(eq("update time_slots set start_lesson = ?, end_lesson = ? where timeslot_id = ?"),
                eq(LocalDateTime.of(2020, 12, 14, 10, 30)),
                eq(LocalDateTime.of(2020, 12, 14, 12, 30)),
                eq(1L)))
                .thenReturn(1);

        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setTimeSlotId(1L);
        timeSlot.setStartLesson(LocalDateTime.of(2020, 12, 14, 10, 30));
        timeSlot.setEndLesson(LocalDateTime.of(2020, 12, 14, 12, 30));

        boolean isUpdated = timeSlotDao.update(timeSlot);

        assertTrue(isUpdated);
    }

    @Test
    public void shouldDeleteTimeSLot() {
        when(jdbcTemplate.update(eq("delete from time_slots where timeslot_id = ?"),
                eq(1L))).
                thenReturn(1);

        boolean isDeleted = timeSlotDao.delete(1L);

        assertTrue(isDeleted);
    }

    @Test
    public void shouldReturnAppropriateName() {
        when(jdbcTemplate.queryForObject(eq("select * from time_slots where timeslot_id = ?"), eq(new Object[]{100L}), (TimeSLotMapper) any()))
                .thenReturn(getMeTestTimeSlot());

        TimeSlot timeSlot = timeSlotDao.getById(100L);

        assertNotNull(timeSlot);
        assertEquals("2020-01-01T12:30", timeSlot.getStartLesson().toString());
        assertEquals("2020-01-01T14:30", timeSlot.getEndLesson().toString());
    }

    private TimeSlot getMeTestTimeSlot() {
        LocalDateTime start = LocalDateTime.parse("2020-01-01T12:30:00");
        LocalDateTime end = start.plusHours(2);
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setTimeSlotId(100L);
        timeSlot.setStartLesson(start);
        timeSlot.setEndLesson(end);

        return timeSlot;
    }
    @Test
    public void shouldOutputAppropriateSentencesWhenDateIsNull() {
        TimeSlot timeSlot = new TimeSlot(1L,null,
               null,new Group(),new Lector());

        Set<ConstraintViolation<TimeSlot>> constraintViolations =
                validator.getValidatorInstance().validate(timeSlot);


        assertEquals(2, constraintViolations.size());
        assertEquals(
                "не должно равняться null",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void shouldOutputAppropriateSentencesWhenDateIsPassed() {
        TimeSlot timeSlot = new TimeSlot(1L,LocalDateTime.of(2019,6,10,10,15),
                LocalDateTime.of(2019,6,10,12,15),new Group(),new Lector());

        Set<ConstraintViolation<TimeSlot>> constraintViolations =
                validator.getValidatorInstance().validate(timeSlot);

        assertEquals(2, constraintViolations.size());
        assertEquals(
                "должно содержать дату, которая еще не наступила",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void shouldPassWhenValid() {
        TimeSlot timeSlot = new TimeSlot(1L,LocalDateTime.of(2021,6,10,10,15),
                LocalDateTime.of(2021,6,10,12,15),new Group(),new Lector());

        Set<ConstraintViolation<TimeSlot>> constraintViolations =
                validator.getValidatorInstance().validate(timeSlot);

        assertEquals(0, constraintViolations.size());
    }

}

