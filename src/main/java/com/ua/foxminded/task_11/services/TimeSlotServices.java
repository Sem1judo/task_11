//package com.ua.foxminded.task_11.services;
//
//import com.ua.foxminded.task_11.dao.impl.TimeSlotDaoImpl;
//import com.ua.foxminded.task_11.exceptions.DaoException;
//import com.ua.foxminded.task_11.exceptions.ServicesException;
//import com.ua.foxminded.task_11.exceptions.WrongInputDataException;
//import com.ua.foxminded.task_11.model.Faculty;
//import com.ua.foxminded.task_11.model.Group;
//import com.ua.foxminded.task_11.model.Lector;
//import com.ua.foxminded.task_11.model.TimeSlot;
//import com.ua.foxminded.task_11.validation.impl.DateValidatorLocalDate;
//import com.ua.foxminded.task_11.validation.impl.TimeSlotValidator;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//
//import javax.validation.Valid;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//@Component
//@Scope(value = "session")
//public class TimeSlotServices {
//    @Autowired
//    private TimeSlotDaoImpl timeSlotDao;
//    @Autowired
//    private DateValidatorLocalDate validatorLocalDate;
//    @Autowired
//    private TimeSlotValidator timeSlotValidator;
//
//    public Optional<List<TimeSlot>> getTimeSlots() {
//        return Optional.ofNullable(timeSlotDao.getAll());
//    }
//
//    public void createNewTimeSlot(LocalDateTime startLesson, LocalDateTime endLesson, Lector lector, Group group) {
//        TimeSlot timeSlot = new TimeSlot();
//        timeSlot.setStartLesson(startLesson);
//        timeSlot.setEndLesson(endLesson);
//        timeSlot.setLector(lector);
//        timeSlot.setGroup(group);
//        timeSlotDao.create(timeSlot);
//    }
//
//    public void deleteTimeSlot(long id) {
//        timeSlotDao.delete(id);
//    }
//
//    public Optional<TimeSlot> getTimeSlot(long id) throws ServicesException {
//        if (!timeSlotDao.getById(id)) {
//
//            try {
//                throw new DaoException("Can't get object");
//            } catch (DaoException e) {
//                e.printStackTrace();
//            }
//        }
//        return Optional.ofNullable();
//
//    }
//
//    public void updateTimeSlot(long id, String startLesson, String endLesson, Lector lector, Group group)
//            throws ServicesException {
//        try {
//            if (id == 0) {
//                throw new WrongInputDataException("Missing id");
//            }
//            TimeSlot timeSlot = timeSlotDao.getById(id);
//
//            if (timeSlotValidator.isValid(timeSlot)) {
//                if ((validatorLocalDate.isValid(startLesson)) && validatorLocalDate.isValid(endLesson)) {
//                    timeSlot.setStartLesson(LocalDateTime.parse(startLesson));
//                    timeSlot.setEndLesson(LocalDateTime.parse(startLesson));
//                    timeSlot.setLector(lector);
//                    timeSlot.setGroup(group);
//                } else {
//                    throw new WrongInputDataException("Incorrect time was inputted");
//                }
//            } else {
//                throw new DaoException("Invalid timeSlot");
//            }
//
//            if (!timeSlotDao.update(timeSlot)) {
//                throw new DaoException("Problem with updating faculty");
//            }
//        } catch (ServicesException | DaoException e) {
//            throw new WrongInputDataException("Problem with updating faculty", e);
//        }
//    }
//}
//
