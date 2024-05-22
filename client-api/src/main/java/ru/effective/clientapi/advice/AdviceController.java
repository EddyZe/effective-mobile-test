package ru.effective.clientapi.advice;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.effective.commons.exceptions.*;
import ru.effective.commons.models.ErrorResponse;


import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class AdviceController {


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> userNotFoundExceptionHandler(UserNotFoundException e) {
        log.error("User not found!");
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> AuthenticationExceptionHandler(AuthenticationException e) {
        ErrorResponse errorResponse = new ErrorResponse("Неверный логин или пароль!", LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserInvalidException.class)
    public ResponseEntity<ErrorResponse> userInvalidExceptionHandler(UserInvalidException e) {
        log.error("User invalid");
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(PhoneNumberIsAlreadyException.class)
    public ResponseEntity<ErrorResponse> handlerPhoneNumberIsAlreadyException(PhoneNumberIsAlreadyException e) {
        log.error("The phone number is already occupied!");
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailIsAlreadyException.class)
    public ResponseEntity<ErrorResponse> handlerEmailIsAlreadyException(EmailIsAlreadyException e) {
        log.error("The email is already occupied!");
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PhoneNumberInvalidException.class)
    public ResponseEntity<ErrorResponse> handlerPhoneNumberInvalidException(PhoneNumberInvalidException e) {
        log.error("Phone number invalid");
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PhoneNumberNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerPhoneNumberNotFoundException(PhoneNumberNotFoundException e) {
        log.error("Phone number not found");
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAddressNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerEmailAddressNotFoundException(EmailAddressNotFoundException e) {
        log.error("Email address not found");
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailInvalidException.class)
    public ResponseEntity<ErrorResponse> handlerEmailInvalidException(EmailInvalidException e) {
        log.error("Email invalid");
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MoneyTransferException.class)
    public ResponseEntity<ErrorResponse> handlerMoneyTransferException(MoneyTransferException e) {
        log.error("Transfer error");
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
