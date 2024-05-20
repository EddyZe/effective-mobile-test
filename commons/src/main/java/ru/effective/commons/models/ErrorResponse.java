package ru.effective.commons.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;



@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

    private String message;
    private LocalDateTime timestamp;
}
