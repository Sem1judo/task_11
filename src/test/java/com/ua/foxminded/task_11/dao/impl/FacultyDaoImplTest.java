package com.ua.foxminded.task_11.dao.impl;

import com.ua.foxminded.task_11.model.Faculty;
import com.ua.foxminded.task_11.model.Group;
import com.ua.foxminded.task_11.model.Lector;
import com.ua.foxminded.task_11.model.mapper.FacultyMapper;
import com.ua.foxminded.task_11.validation.ValidatorEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import javax.validation.ConstraintViolation;

import java.util.ArrayList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class FacultyDaoImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private FacultyDaoImpl facultyDao;

    @InjectMocks
    private ValidatorEntity validator;


    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(facultyDao, "jdbcTemplate", jdbcTemplate);

    }

    @Test
    public void shouldCreateFaculty() {
        when(jdbcTemplate.update(eq("insert into faculties(faculty_name) values(?)"),
                eq("testName")))
                .thenReturn(1);

        Faculty faculty = new Faculty();
        faculty.setName("testName");

        boolean isCreated = facultyDao.create(faculty);
        assertTrue(isCreated);
    }

    @Test
    public void shouldUpdatedFaculty() {
        when(jdbcTemplate.update(eq("update faculties set faculty_name = ? where faculty_id = ?"), eq("testName"), eq(1L)))
                .thenReturn(1);

        Faculty faculty = new Faculty();
        faculty.setFacultyId(1L);
        faculty.setName("testName");
        boolean isUpdated = facultyDao.update(faculty);

        assertTrue(isUpdated);
    }

    @Test
    public void shouldDeleteFaculty() {
        when(jdbcTemplate.update(eq("delete from faculties where faculty_id = ?"), eq(1L))).
                thenReturn(1);

        boolean isDeleted = facultyDao.delete(1L);

        assertTrue(isDeleted);
    }

    @Test
    public void shouldReturnAppropriateName() {
        when(jdbcTemplate.queryForObject(eq("select * from faculties where faculty_id = ?"), eq(new Object[]{100L})
                , (FacultyMapper) any())).thenReturn(getMeTestFaculty());

        Faculty faculty = facultyDao.getById(100L);

        assertNotNull(faculty);
        assertEquals("testName", faculty.getName());
    }

    private Faculty getMeTestFaculty() {
        Faculty faculty = new Faculty();
        faculty.setFacultyId(100L);
        faculty.setName("testName");

        return faculty;
    }

    @Test
    public void shouldOutputExceptionWhenNameIsNull() {
        Faculty faculty = new Faculty(1, null, new ArrayList<>(), new ArrayList<>());

        Set<ConstraintViolation<Faculty>> constraintViolations =
                validator.getValidatorInstance().validate(faculty);

        assertEquals(1, constraintViolations.size());
        assertEquals(
                "не должно быть пустым",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void shouldOutputAppropriateSentencesWhenNameTooShort() {
        Faculty faculty = new Faculty(1, "D", new ArrayList<>(), new ArrayList<>());

        Set<ConstraintViolation<Faculty>> constraintViolations =
                validator.getValidatorInstance().validate(faculty);

        assertEquals(1, constraintViolations.size());
        assertEquals(
                "Faculty name must be between 3 and 20 characters",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void shouldPassWhenValid() {
        Faculty faculty = new Faculty(1, "ValidName", new ArrayList<>(), new ArrayList<>());

        Set<ConstraintViolation<Faculty>> constraintViolations =
                validator.getValidatorInstance().validate(faculty);

        assertEquals(0, constraintViolations.size());
    }
}


