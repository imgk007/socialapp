package com.example.socialapp.exception;

import java.time.LocalDate;
import java.time.LocalDateTime;

//error response structure of exception
//ResponseEntityExceptionHandler handles whenever any exception happens in Spring MVC
public class ErrorDetails {

    private LocalDateTime timeStamp;
    private String message;
    private String details;
    public ErrorDetails(LocalDateTime timeStamp, String message, String details) {
        this.timeStamp = timeStamp;
        this.message = message;
        this.details = details;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }


}

