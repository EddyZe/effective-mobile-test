package ru.effective.clientapi.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
public class EmailAddressController {

    private final UserService userService;
    private final EmailAddressService emailAddressService;

    @PostMapping("{username}/add-email")
    public ResponseEntity<HttpStatus> addEmail(@PathVariable("username") String username,
                                               @RequestBody @Valid EmailAddressDTO emailAddressDTO,
                                               BindingResult bindingResult) {

        handlerBindingResult(bindingResult);

        User user = userService.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден!"));

        emailAddressService.save(convertToEmailAddress(user, emailAddressDTO));
        userService.update(user);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("{username}/remove-email")
    public ResponseEntity<HttpStatus> removePhoneNumber(@PathVariable("username") String username,
                                                        @RequestBody @Valid EmailAddressDTO emailAddressDTO,
                                                        BindingResult bindingResult) {

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
