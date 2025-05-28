package com.gymmanager.exception;

public class MemberException extends ServiceException {
    public MemberException(String message) {
        super(message);
    }

    public static class MemberNotFoundException extends MemberException {
        public MemberNotFoundException(int memberId) {
            super("Member not found with ID: " + memberId);
        }
    }

    public static class InvalidMemberDataException extends MemberException {
        public InvalidMemberDataException(String message) {
            super(message);
        }
    }

    public static class DuplicateMemberException extends MemberException {
        public DuplicateMemberException(String firstName, String lastName) {
            super("Member already exists with identifier: " + firstName + " " + lastName);
        }
    }
}
