package com.ua.foxminded.task_11.services;

import com.ua.foxminded.task_11.dao.impl.LessonDaoImpl;
import com.ua.foxminded.task_11.model.Lector;
import com.ua.foxminded.task_11.model.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LessonServices {

    @Autowired
    private LessonDaoImpl lessonDao;

    public List<Lesson> getLessons() {
        return lessonDao.getAll();
    }

    public void createNewLesson(String nameLesson, Lector lector) {
        Lesson lesson = new Lesson();
        lesson.setName(nameLesson);
        lesson.setLector(lector);
        lessonDao.create(lesson);
    }

    public void deleteLesson(long id) {
        lessonDao.delete(id);
    }

    public Lesson getLesson(long id) {
        return lessonDao.getById(id);
    }

    public void updateLesson(long id, String nameLesson, Lector lector) {
        Lesson lesson = lessonDao.getById(id);
        lesson.setName(nameLesson);
        lesson.setLector(lector);
        lessonDao.update(lesson);
    }
}
