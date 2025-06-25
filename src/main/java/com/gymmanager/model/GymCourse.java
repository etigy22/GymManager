package com.gymmanager.model;

public class GymCourse {
    private final int courseId;
    private final String name;
    private final String instructorFirstName;
    private final String instructorLastName;
    private final int maxCapacity;

    public GymCourse(int courseId, String name, String instructorFirstName, String instructorLastName, int maxCapacity) {
        this.courseId = courseId;
        this.name = name;
        this.instructorFirstName = instructorFirstName;
        this.instructorLastName = instructorLastName;
        this.maxCapacity = maxCapacity;
    }

    public int getCourseId() {
        return courseId;
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


