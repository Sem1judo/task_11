package com.ua.foxminded.task_11.model;

import java.util.List;
import java.util.Objects;

public class Faculty {
    private long facultyId;
    private String name;
    private List<Group> groups;
    private List<Lector> lectors;

    public Faculty() {
    }

    public Faculty(String name) {
        this.name = name;
    }

    public Faculty(long facultyId, String name, List<Group> groups, List<Lector> lectors) {
        this.facultyId = facultyId;
        this.name = name;
        this.groups = groups;
        this.lectors = lectors;
    }

    public long getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(long facultyId) {
        this.facultyId = facultyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Lector> getLectors() {
        return lectors;
    }

    public void setLectors(List<Lector> lectors) {
        this.lectors = lectors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return facultyId == faculty.facultyId &&
                Objects.equals(name, faculty.name) &&
                Objects.equals(groups, faculty.groups) &&
                Objects.equals(lectors, faculty.lectors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(facultyId, name, groups, lectors);
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "facultyId=" + facultyId +
                ", name='" + name + '\'' +
                ", groups=" + groups +
                ", lectors=" + lectors +
                '}';
    }
}
