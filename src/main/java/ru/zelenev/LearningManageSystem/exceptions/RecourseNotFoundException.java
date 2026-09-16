package ru.zelenev.LearningManageSystem.exceptions;

public class RecourseNotFoundException extends RuntimeException {
    public RecourseNotFoundException(String message) {
        super(message);
    }
}
