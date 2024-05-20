package ru.effective.clientapi.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ru.effective.clientapi.security.AuthenticationResponse;
import ru.effective.clientapi.services.AuthService;
import ru.effective.commons.DTO.UserSingInDTO;
import ru.effective.commons.exceptions.UserInvalidException;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody @Valid UserSingInDTO userSingInDTO, BindingResult bindingResult) {

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

            throw new UserInvalidException(errorsMessage.toString());
        }

        return ResponseEntity.ok(authService.login(userSingInDTO));
    }

}
