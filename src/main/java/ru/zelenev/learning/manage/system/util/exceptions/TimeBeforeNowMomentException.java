package ru.zelenev.learning.manage.system.util.exceptions;

public class TimeBeforeNowMomentException extends RuntimeException {
    public TimeBeforeNowMomentException(String message) {
        super(message);
    }
}
