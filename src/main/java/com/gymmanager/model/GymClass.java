package com.gymmanager.model;

public class GymClass {
    private final int classId;
    private final String name;
    private final String instructorFirstName;
    private final String instructorLastName;
    private final int maxCapacity;

    public GymClass(int classId, String name, String instructorFirstName, String instructorLastName, int maxCapacity) {
        this.classId = classId;
        this.name = name;
        this.instructorFirstName = instructorFirstName;
        this.instructorLastName = instructorLastName;
        this.maxCapacity = maxCapacity;
    }

    public int getClassId() {
        return classId;
    }

    public String getName() {
        return name;
    }

    public String getInstructorFirstName() {
        return instructorFirstName;
    }

    public String getInstructorLastName() {
        return instructorLastName;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

}


