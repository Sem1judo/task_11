package com.ua.foxminded.task_11.model;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

public class TimeSlot {
    private long timeSlotId;
    @NotNull
    @Future
    private LocalDateTime startLesson;
    @NotNull
    @Future
    private LocalDateTime endLesson;
    private Group group;
    private Lector lector;

    public TimeSlot() {
    }

    public TimeSlot(LocalDateTime startLesson, LocalDateTime endLesson) {
        this.startLesson = startLesson;
        this.endLesson = endLesson;
    }

    public TimeSlot(long timeSlotId, LocalDateTime startLesson, LocalDateTime endLesson, Group group, Lector lector) {
        this.timeSlotId = timeSlotId;
        this.startLesson = startLesson;
        this.endLesson = endLesson;
        this.group = group;
        this.lector = lector;
    }

    public long getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(long timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public LocalDateTime getStartLesson() {
        return startLesson;
    }

    public void setStartLesson(LocalDateTime startLesson) {
        this.startLesson = startLesson;
    }

    public LocalDateTime getEndLesson() {
        return endLesson;
    }

    public void setEndLesson(LocalDateTime endLesson) {
        this.endLesson = endLesson;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Lector getLector() {
        return lector;
    }

    public void setLector(Lector lector) {
        this.lector = lector;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeSlot timeSlot = (TimeSlot) o;
        return timeSlotId == timeSlot.timeSlotId &&
                Objects.equals(startLesson, timeSlot.startLesson) &&
                Objects.equals(endLesson, timeSlot.endLesson) &&
                Objects.equals(group, timeSlot.group) &&
                Objects.equals(lector, timeSlot.lector);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeSlotId, startLesson, endLesson, group, lector);
    }

    @Override
    public String toString() {
        return "TimeSlot{" +
                "TimeSlotId=" + timeSlotId +
                ", startLesson=" + startLesson +
                ", endLesson=" + endLesson +
                ", group=" + group +
                ", lector=" + lector +
                '}';
    }
}
