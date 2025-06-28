package com.gymmanager.model;
/** Represents a gym course with its details. */
public abstract class Person {
    protected String firstName;
    protected String lastName;
    /**
     * Constructs a Person with the specified first and last name.
     *
     * @param firstName the first name of the person
     * @param lastName the last name of the person
     */
    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    /** Gets the first name of the person. */
    public String getFirstName() {
        return firstName;
    }
    /** Gets the last name of the person. */
    public String getLastName() {
        return lastName;
    }
}
