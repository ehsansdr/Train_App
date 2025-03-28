package com.example.trainproject.base.Exception;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Map;

@Getter
public class ErrorResponse {

    private String title;

    private String message;

    private Map<String, String> details;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;


    public ErrorResponse(String title, String message, Map details) {
        this.title = title;
        this.message = message;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(String title, String message) {
        this(title, message, null);
    }

    public ErrorResponse(String title) {
        this(title, null);
    }

}

