package ru.zelenev.learning_manage_system.util.exceptions;

public class TimeBeforeNowMomentException extends RuntimeException {
    public TimeBeforeNowMomentException(String message) {
        super(message);
    }
}
