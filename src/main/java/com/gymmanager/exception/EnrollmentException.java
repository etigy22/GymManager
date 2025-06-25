package com.gymmanager.exception;

public class EnrollmentException extends ServiceException {
    public EnrollmentException(String message) {
        super(message);
    }

    public static class DuplicateEnrollmentException extends EnrollmentException {
        public DuplicateEnrollmentException(int memberId, int courseId) {
            super("Member ID: " + memberId + " is already enrolled in course ID: " + courseId);
        }
    }

    public static class EnrollmentNotAllowedException extends EnrollmentException {
        public EnrollmentNotAllowedException() {
            super("Standard members cannot enroll in courses. Please upgrade to Premium membership.");
        }
    }
}
