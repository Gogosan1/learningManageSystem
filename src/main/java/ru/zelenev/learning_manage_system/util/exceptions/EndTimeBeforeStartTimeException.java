package ru.zelenev.learning_manage_system.util.exceptions;

public class EndTimeBeforeStartTimeException extends RuntimeException {
    public EndTimeBeforeStartTimeException(String message) {
        super(message);
    }
}
