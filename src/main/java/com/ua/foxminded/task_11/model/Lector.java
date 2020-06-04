package com.ua.foxminded.task_11.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class Lector {

    private Long lectorId;
    private Long facultyId;
    @NotNull
    @Size(min=3, max=50)
    private String firstName;
    @NotNull
    @Size(min=3, max=50)
    private String lastName;

    public Lector() {
    }

    public Lector(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Lector(Long lectorId, String firstName, String lastName) {
        this.lectorId = lectorId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Lector(Long lectorId, Long facultyId, String firstName, String lastName) {
        this.lectorId = lectorId;
        this.facultyId = facultyId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getLectorId() {
        return lectorId;
    }

    public void setLectorId(Long lectorId) {
        this.lectorId = lectorId;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Long facultyId) {
        this.facultyId = facultyId;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lector lector = (Lector) o;
        return Objects.equals(lectorId, lector.lectorId) &&
                Objects.equals(facultyId, lector.facultyId) &&
                Objects.equals(firstName, lector.firstName) &&
                Objects.equals(lastName, lector.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lectorId, facultyId, firstName, lastName);
    }

    @Override
    public String toString() {
        return "Lector{" +
                "lectorId=" + lectorId +
                ", facultyId=" + facultyId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
