package com.ua.foxminded.task_11.dao.impl;

import com.ua.foxminded.task_11.model.Faculty;
import com.ua.foxminded.task_11.model.Group;
import com.ua.foxminded.task_11.model.mapper.GroupMapper;
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

class GroupDaoImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;
    @InjectMocks
    private GroupDaoImpl groupDao;
    @InjectMocks
    private ValidatorEntity validator;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(groupDao, "jdbcTemplate", jdbcTemplate);
    }

    @Test
    public void shouldCreateGroup() {
        when(jdbcTemplate.update(eq("insert into groups(group_name) values(?)"),eq("testName")))
                .thenReturn(1);

        Group group = new Group();
        group.setName("testName");

        boolean isCreated = groupDao.create(group);
        assertTrue(isCreated);
    }

    @Test
    public void shouldUpdatedGroup() {
        when(jdbcTemplate.update(eq("update groups set group_name where group_id = ?"), eq("testName"), eq(1L)))
                .thenReturn(1);

        Group group = new Group();
        group.setGroupId(1L);
        group.setName("testName");
        boolean isUpdated = groupDao.update(group);

        assertTrue(isUpdated);
    }

    @Test
    public void shouldDeleteGroup() {
        when(jdbcTemplate.update(eq("delete from groups where group_id = ?"), eq(1L))).thenReturn(1);

        boolean isDeleted = groupDao.delete(1L);

        assertTrue(isDeleted);
    }

    @Test
    public void shouldReturnAppropriateName() {
        when(jdbcTemplate.queryForObject(eq("select * from groups where group_id = ?"),eq(new Object[]{100L})
                ,(GroupMapper) any())).thenReturn(getMeTestGroup());

        Group group = groupDao.getById(100L);

        assertNotNull(group);
        assertEquals("testName", group.getName());
    }

    private Group getMeTestGroup() {
        Group group = new Group();
        group.setGroupId(100L);
        group.setName("testName");

        return group;
    }
    @Test
    public void shouldOutputAppropriateSentencesWhenNameIsNull() {
        Group group = new Group(1,1,null);

        Set<ConstraintViolation<Group>> constraintViolations =
                validator.getValidatorInstance().validate(group);

        assertEquals(1, constraintViolations.size());
        assertEquals(
                "не должно быть пустым",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void shouldOutputAppropriateSentencesWhenNameTooShort() {
        Group group = new Group(1,1,"F");

        Set<ConstraintViolation<Group>> constraintViolations =
                validator.getValidatorInstance().validate(group);

        assertEquals(2, constraintViolations.size());
        assertEquals(
                "Group name must be alphanumeric with no spaces",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void shouldPassWhenValid() {
        Group group = new Group(1,1,"Fb-12");

        Set<ConstraintViolation<Group>> constraintViolations =
                validator.getValidatorInstance().validate(group);

        assertEquals(0, constraintViolations.size());
    }

}

