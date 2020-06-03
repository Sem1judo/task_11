package com.ua.foxminded.task_11.services;

import com.ua.foxminded.task_11.dao.impl.LessonDaoImpl;
import com.ua.foxminded.task_11.model.Lector;
import com.ua.foxminded.task_11.model.Lesson;

import java.util.List;

public class LessonServices {

    private LessonDaoImpl lessonDao = new LessonDaoImpl();

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
