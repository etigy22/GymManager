package com.gymmanager.exception;

/** Exception related to member operations in the gym manager application. */
public class MemberException extends ServiceException {
    /** Constructs a new MemberException with the specified detail message. */
    public MemberException(String message) {
        super(message);
    }

    /** Thrown when a member with the specified ID is not found. */
    public static class MemberNotFoundException extends MemberException {
        /** Constructs a new MemberNotFoundException for the given member ID. */
        public MemberNotFoundException(int memberId) {
            super("Member not found with ID: " + memberId);
        }
    }

    /** Thrown when invalid data is provided for a member. */
    public static class InvalidMemberDataException extends MemberException {
        /** Constructs a new InvalidMemberDataException with the specified message. */
        public InvalidMemberDataException(String message) {
            super(message);
        }
    }

    /** Thrown when a duplicate member is detected. */
    public static class DuplicateMemberException extends MemberException {
        /** Constructs a new DuplicateMemberException for the given member name. */
        public DuplicateMemberException(String firstName, String lastName) {
            super("Member already exists with identifier: " + firstName + " " + lastName);
        }
    }
}
