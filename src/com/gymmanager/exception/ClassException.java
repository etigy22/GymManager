package com.gymmanager.exception;

public class ClassException extends ServiceException {
    public ClassException(String message) {
        super(message);
    }

    public static class ClassNotFoundException extends ClassException {
        public ClassNotFoundException(int classId) {
            super("Class not found with ID: " + classId);
        }
    }

    public static class ClassFullException extends ClassException {
        public ClassFullException(int classId) {
            super("Class with ID: " + classId + " is at full capacity");
        }
    }

    public static class InvalidClassDataException extends ClassException {
        public InvalidClassDataException(String message) {
            super(message);
        }
    }
}