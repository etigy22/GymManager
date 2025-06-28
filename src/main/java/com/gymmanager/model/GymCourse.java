package com.gymmanager.model;
/** Represents a gym course with its details. */
public class GymCourse {
    private final int courseId;
    private final String name;
    private final String instructorFirstName;
    private final String instructorLastName;
    private final int maxCapacity;
    /** Constructs a GymCourse with the specified details. */
    public GymCourse(int courseId, String name, String instructorFirstName, String instructorLastName, int maxCapacity) {
        this.courseId = courseId;
        this.name = name;
        this.instructorFirstName = instructorFirstName;
        this.instructorLastName = instructorLastName;
        this.maxCapacity = maxCapacity;
    }
    /** Gets the unique identifier for the course. */
    public int getCourseId() {
        return courseId;
    }
    /** Gets the name of the course. */
    public String getName() {
        return name;
    }
    /** Gets the first name of the instructor. */
    public String getInstructorFirstName() {
        return instructorFirstName;
    }
    /** Gets the last name of the instructor. */
    public String getInstructorLastName() {
        return instructorLastName;
    }
    /** Gets the maximum number of participants allowed in the course. */
    public int getMaxCapacity() {
        return maxCapacity;
    }
}


