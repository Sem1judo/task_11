package com.ua.foxminded.task_12.model;

import java.util.List;
import java.util.Objects;

public class TimeTable {
    private List<TimeSlot> timeSlots;

    public TimeTable() {
    }

    public TimeTable(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }

    public void addLesson(TimeSlot timeSlot){
        timeSlots.add(timeSlot);
    }

    public void removeLesson(TimeSlot timeSlot){
        timeSlots.remove(timeSlot);
    }

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeTable timeTable = (TimeTable) o;
        return Objects.equals(timeSlots, timeTable.timeSlots);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeSlots);
    }

    @Override
    public String toString() {
        return "TimeTable{" +
                "timeSlots=" + timeSlots +
                '}';
    }
}
