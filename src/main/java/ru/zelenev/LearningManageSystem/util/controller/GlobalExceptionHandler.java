package ru.zelenev.LearningManageSystem.util.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.zelenev.LearningManageSystem.util.entity.ErrorResponse;
import ru.zelenev.LearningManageSystem.util.exceptions.EndTimeBeforeStartTimeException;
import ru.zelenev.LearningManageSystem.util.exceptions.ResourceNotFoundException;
import ru.zelenev.LearningManageSystem.util.exceptions.TimeBeforeNowMomentException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException exception){
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Resource not found",
                exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EndTimeBeforeStartTimeException.class)
    public ResponseEntity<ErrorResponse> handleEndTimeBeforeStartTimeException(EndTimeBeforeStartTimeException exception){
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.UNPROCESSABLE_CONTENT.value(),
                "Invalid time",
                exception.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.UNPROCESSABLE_CONTENT);
    }

    @ExceptionHandler(TimeBeforeNowMomentException.class)
    public ResponseEntity<ErrorResponse> handleTimeBeforeNowMomentException(TimeBeforeNowMomentException exception){
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.UNPROCESSABLE_CONTENT.value(),
                "Invalid time",
                exception.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.UNPROCESSABLE_CONTENT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception exception){
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal server error",
                exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
