package ru.effective.clientapi.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.effective.clientapi.services.EmailAddressService;
import ru.effective.clientapi.services.UserService;
import ru.effective.commons.DTO.EmailAddressDTO;
import ru.effective.commons.entities.EmailAddress;
import ru.effective.commons.entities.User;
import ru.effective.commons.exceptions.EmailInvalidException;
import ru.effective.commons.exceptions.PhoneNumberInvalidException;
import ru.effective.commons.exceptions.PhoneNumberNotFoundException;
import ru.effective.commons.exceptions.UserNotFoundException;

@RestController
@RequiredArgsConstructor
@RequestMapping("email")
@Slf4j
public class EmailAddressController {

    private final UserService userService;
    private final EmailAddressService emailAddressService;

    @PostMapping("{username}/add-email")
    @Operation(summary = "Добавляет email", description = "Позволяет добавить email")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<HttpStatus> addEmail(@PathVariable("username") String username,
                                               @RequestBody @Valid EmailAddressDTO emailAddressDTO,
                                               BindingResult bindingResult) {
        log.info("%s add new email".formatted(username));
        handlerBindingResult(bindingResult);

        User user = userService.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден!"));

        emailAddressService.save(convertToEmailAddress(user, emailAddressDTO));
        userService.update(user);
        log.info("%s successfully added the email");

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("{username}/remove-email")
    @Operation(summary = "Удаляет email")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<HttpStatus> removePhoneNumber(@PathVariable("username") String username,
                                                        @RequestBody @Valid EmailAddressDTO emailAddressDTO,
                                                        BindingResult bindingResult) {

        log.info("%s remove email".formatted(username));
        handlerBindingResult(bindingResult);

        User user = userService.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден!"));

        EmailAddress emailAddress = emailAddressService.findByEmail(emailAddressDTO.getEmail())
                .orElseThrow(() -> new PhoneNumberNotFoundException("Email не найден"));

        if (!emailAddress.getUser().getUsername().equals(user.getUsername()))
            throw new EmailInvalidException("Удалить можно только свой email!");

        if (emailAddressService.findByUserUsername(username).size() == 1)
            throw new PhoneNumberInvalidException("Нельзя удалить единственный email!");

        emailAddressService.delete(emailAddress);
        log.info("%s successfully deleted the email".formatted(username));

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

            throw new EmailInvalidException(errorsMessage.toString());
        }
    }

    private EmailAddress convertToEmailAddress(User user, EmailAddressDTO emailAddressDTO) {
        return EmailAddress.builder()
                .user(user)
                .email(emailAddressDTO.getEmail())
                .build();
    }
}
