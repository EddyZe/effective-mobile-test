package ru.effective.support.advice;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.effective.commons.exceptions.EmailIsAlreadyException;
import ru.effective.commons.exceptions.PhoneNumberIsAlreadyException;
import ru.effective.commons.exceptions.UserInvalidException;
import ru.effective.commons.exceptions.UsernameIsAlreadyException;
import ru.effective.commons.models.ErrorResponse;

import java.time.LocalDateTime;


@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(UserInvalidException.class)
    private ResponseEntity<ErrorResponse> handlerUserInvalidException(UserInvalidException e) {
        log.error("Failed to create a new user");
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PhoneNumberIsAlreadyException.class)
    private ResponseEntity<ErrorResponse> handlerPhoneNumberIsAlreadyException(PhoneNumberIsAlreadyException e) {
        log.error("The phone number is already occupied!");
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameIsAlreadyException.class)
    private ResponseEntity<ErrorResponse> handlerUsernameIsAlreadyException(UsernameIsAlreadyException e) {
        log.error("The username is already occupied!");
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailIsAlreadyException.class)
    private ResponseEntity<ErrorResponse> handlerEmailIsAlreadyException(EmailIsAlreadyException e) {
        log.error("The email is already occupied!");
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
