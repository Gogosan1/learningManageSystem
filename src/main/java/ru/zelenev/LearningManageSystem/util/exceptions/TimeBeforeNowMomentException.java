package ru.zelenev.LearningManageSystem.util.exceptions;

public class TimeBeforeNowMomentException extends RuntimeException {
    public TimeBeforeNowMomentException(String message) {
        super(message);
    }
}
