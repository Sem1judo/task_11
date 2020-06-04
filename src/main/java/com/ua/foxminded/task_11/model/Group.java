package com.ua.foxminded.task_11.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class Group {

    private long groupId;
    private long facultyId;
    @NotNull
    @Size(min=3, max=50)
    private String name;

    public Group() {
    }

    public Group(String name) {
        this.name = name;
    }

    public Group(long groupId, long facultyId, String name) {
        this.groupId = groupId;
        this.facultyId = facultyId;
        this.name = name;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return groupId == group.groupId &&
                facultyId == group.facultyId &&
                Objects.equals(name, group.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, facultyId, name);
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupId=" + groupId +
                ", facultyId=" + facultyId +
                ", name='" + name + '\'' +
                '}';
    }
}
