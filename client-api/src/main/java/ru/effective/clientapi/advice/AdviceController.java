package ru.effective.clientapi.advice;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.effective.commons.exceptions.UserInvalidException;
import ru.effective.commons.exceptions.UserNotFoundException;
import ru.effective.commons.models.ErrorResponse;


import java.time.LocalDateTime;

@RestControllerAdvice
public class AdviceController {


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> userInvalidExceptionHandler(UserNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> userInvalidExceptionHandler(AuthenticationException e) {
        ErrorResponse errorResponse = new ErrorResponse("Неверный логин или пароль!", LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserInvalidException.class)
    public ResponseEntity<ErrorResponse> userInvalidExceptionHandler(UserInvalidException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


}
