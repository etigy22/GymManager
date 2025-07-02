package com.gymmanager.model;
/** Represents an instructor in the gym management system. This class and its functionality are not in use yet. Just to show Inheritance */
public class Instructor extends Person {
    private final int instructorId;

    public Instructor(int instructorId, String firstName, String lastName) {
        super(firstName, lastName);
        this.instructorId = instructorId;
    }
    /** Gets the unique identifier for the instructor. */
    public int getInstructorId() {
        return instructorId;
    }

    /** Instructor Information */
    @Override
    public String toString() {
        return "Instructor ID: " + instructorId + ", Name: " + firstName + " " + lastName;
    }
}
