package ru.effective.clientapi.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import  jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.effective.clientapi.services.PhoneNumberService;
import ru.effective.clientapi.services.UserService;
import ru.effective.commons.DTO.PhoneNumberDTO;
import ru.effective.commons.entities.PhoneNumber;
import ru.effective.commons.entities.User;
import ru.effective.commons.exceptions.PhoneNumberInvalidException;
import ru.effective.commons.exceptions.PhoneNumberNotFoundException;
import ru.effective.commons.exceptions.UserNotFoundException;

@RestController
@RequiredArgsConstructor
@RequestMapping("phone-number")
@Slf4j
public class PhoneNumberController {
    private final PhoneNumberService phoneNumberService;
    private final UserService userService;

    @PostMapping("{username}/add-number")
    @Operation(summary = "добавляет номер")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<HttpStatus> addPhoneNumber(@PathVariable("username") String username,
                                                     @RequestBody @Valid PhoneNumberDTO phoneNumberDTO,
                                                     BindingResult bindingResult) {
        log.info("%s add phone number".formatted(username));
        handlerBindingResult(bindingResult);

        User user = userService.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден!"));
        
        phoneNumberService.save(convertToPhoneNumber(user, phoneNumberDTO));
        userService.update(user);
        log.info("%s successfully added the phone number".formatted(username));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("{username}/remove-number")
    @Operation(summary = "Удаляет номер")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<HttpStatus> removePhoneNumber(@PathVariable("username") String username,
                                                     @RequestBody @Valid PhoneNumberDTO phoneNumberDTO,
                                                     BindingResult bindingResult) {
        log.info("%s remove phone number");
        handlerBindingResult(bindingResult);

        User user = userService.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден!"));

        PhoneNumber phoneNumber = phoneNumberService.findByNumber(phoneNumberDTO.getPhoneNumber())
                .orElseThrow(() -> new PhoneNumberNotFoundException("Номер не найден!"));

        if (!phoneNumber.getUser().getUsername().equals(user.getUsername()))
            throw new PhoneNumberInvalidException("Удалить можно только свой номер!");

        if (phoneNumberService.findByUserUsername(username).size() == 1)
            throw new PhoneNumberInvalidException("Нельзя удалить единственный номер!");

        phoneNumberService.delete(phoneNumber);

        log.info("%s successfully deleted the phone number".formatted(username));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    private void handlerBindingResult(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorsMessage = new StringBuilder();

            bindingResult.getFieldErrors().forEach(fieldError -> errorsMessage
                    .append(fieldError.getField()
                            .replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2")
                            .replaceAll("([a-z])([A-Z])", "$1_$2")
                            .toLowerCase())
                    .append(" - ")
                    .append(fieldError.getDefaultMessage())
                    .append("; "));

            throw new PhoneNumberInvalidException(errorsMessage.toString());
        }
    }

    private PhoneNumber convertToPhoneNumber(User user, PhoneNumberDTO phoneNumberDTO) {
        return PhoneNumber.builder()
                .user(user)
                .number(phoneNumberDTO.getPhoneNumber())
                .build();
    }
}
