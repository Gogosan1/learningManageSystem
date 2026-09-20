package ru.zelenev.LearningManageSystem.util.exceptions;

public class EndTimeBeforeStartTimeException extends RuntimeException {
    public EndTimeBeforeStartTimeException(String message) {
        super(message);
    }
}
