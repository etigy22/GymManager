package com.gymmanager.exception;

/** Exception related to enrollment operations in the gym manager application. */
public class EnrollmentException extends ServiceException {
    /** Constructs a new EnrollmentException with the specified detail message. */
    public EnrollmentException(String message) {
        super(message);
    }

    /** Thrown when a member is already enrolled in a course. */
    public static class DuplicateEnrollmentException extends EnrollmentException {
        /** Constructs a new DuplicateEnrollmentException for the given member and course IDs. */
        public DuplicateEnrollmentException(int memberId, int courseId) {
            super("Member ID: " + memberId + " is already enrolled in course ID: " + courseId);
        }
    }

    /** Thrown when enrollment is not allowed for the member. */
    public static class EnrollmentNotAllowedException extends EnrollmentException {
        /** Constructs a new EnrollmentNotAllowedException. */
        public EnrollmentNotAllowedException() {
            super("Standard members cannot enroll in courses. Please upgrade to Premium membership.");
        }
    }
}
