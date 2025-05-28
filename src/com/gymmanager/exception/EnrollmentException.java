package com.gymmanager.exception;

public class EnrollmentException extends ServiceException {
    public EnrollmentException(String message) {
        super(message);
    }

    public static class DuplicateEnrollmentException extends EnrollmentException {
        public DuplicateEnrollmentException(int memberId, int classId) {
            super("Member ID: " + memberId + " is already enrolled in class ID: " + classId);
        }
    }

    public static class EnrollmentNotAllowedException extends EnrollmentException {
        public EnrollmentNotAllowedException() {
            super("Standard members cannot enroll in classes. Please upgrade to Premium membership.");
        }
    }
}
