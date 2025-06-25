package com.gymmanager.exception;

public class CourseException extends ServiceException {
    public CourseException(String message) {
        super(message);
    }

    public static class CourseNotFoundException extends CourseException {
        public CourseNotFoundException(int courseId) {
            super("Course not found with ID: " + courseId);
        }
    }

    public static class CourseFullException extends CourseException {
        public CourseFullException(int courseId) {
            super("Course with ID: " + courseId + " is at full capacity");
        }
    }

    public static class InvalidCourseDataException extends CourseException {
        public InvalidCourseDataException(String message) {
            super(message);
        }
    }
}