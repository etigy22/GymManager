package com.gymmanager.exception;

/** Exception related to course operations in the gym manager application. */
public class CourseException extends ServiceException {
    /** Constructs a new CourseException with the specified detail message. */
    public CourseException(String message) {
        super(message);
    }

    /** Thrown when a course with the specified ID is not found. */
    public static class CourseNotFoundException extends CourseException {
        /** Constructs a new CourseNotFoundException for the given course ID. */
        public CourseNotFoundException(int courseId) {
            super("Course not found with ID: " + courseId);
        }
    }

    /** Thrown when a course has reached its full capacity. */
    public static class CourseFullException extends CourseException {
        /** Constructs a new CourseFullException for the given course ID. */
        public CourseFullException(int courseId) {
            super("Course with ID: " + courseId + " is at full capacity");
        }
    }

    /** Thrown when invalid data is provided for a course. */
    public static class InvalidCourseDataException extends CourseException {
        /** Constructs a new InvalidCourseDataException with the specified message. */
        public InvalidCourseDataException(String message) {
            super(message);
        }
    }
}