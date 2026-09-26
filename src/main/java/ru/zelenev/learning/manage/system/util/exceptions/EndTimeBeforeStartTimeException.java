package ru.zelenev.learning.manage.system.util.exceptions;

public class EndTimeBeforeStartTimeException extends RuntimeException {
    public EndTimeBeforeStartTimeException(String message) {
        super(message);
    }
}
