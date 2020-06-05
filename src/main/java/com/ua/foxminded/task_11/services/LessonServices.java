package com.ua.foxminded.task_11.services;

import com.ua.foxminded.task_11.dao.impl.LessonDaoImpl;
import com.ua.foxminded.task_11.model.Lector;
import com.ua.foxminded.task_11.model.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Component
public class LessonServices {

    @Autowired
    private LessonDaoImpl lessonDao;

    public Optional<List<Lesson>> getLessons() {
        return Optional.ofNullable(lessonDao.getAll());
    }

    public void createNewLesson(@Valid String nameLesson, Lector lector) {
        Lesson lesson = new Lesson();
        lesson.setName(nameLesson);
        lesson.setLector(lector);
        lessonDao.create(lesson);
    }

    public void deleteLesson(long id) {
        lessonDao.delete(id);
    }

    public Optional<Lesson> getLesson(long id) {
        return Optional.ofNullable(lessonDao.getById(id));
    }

    public void updateLesson(long id,@Valid String nameLesson, Lector lector) {
        Lesson lesson = lessonDao.getById(id);
        lesson.setName(nameLesson);
        lesson.setLector(lector);
        lessonDao.update(lesson);
    }
}
