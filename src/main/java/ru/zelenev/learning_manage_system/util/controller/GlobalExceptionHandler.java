package ru.zelenev.learning_manage_system.util.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.zelenev.learning_manage_system.util.entity.ErrorResponse;
import ru.zelenev.learning_manage_system.util.exceptions.EndTimeBeforeStartTimeException;
import ru.zelenev.learning_manage_system.util.exceptions.ResourceNotFoundException;
import ru.zelenev.learning_manage_system.util.exceptions.TimeBeforeNowMomentException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND,
                "Resource not found",
                exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EndTimeBeforeStartTimeException.class)
    public ResponseEntity<ErrorResponse> handleEndTimeBeforeStartTimeException(EndTimeBeforeStartTimeException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.UNPROCESSABLE_CONTENT,
                "Invalid time",
                exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_CONTENT);
    }

    @ExceptionHandler(TimeBeforeNowMomentException.class)
    public ResponseEntity<ErrorResponse> handleTimeBeforeNowMomentException(TimeBeforeNowMomentException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.UNPROCESSABLE_CONTENT,
                "Invalid time",
                exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_CONTENT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal server error",
                exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
