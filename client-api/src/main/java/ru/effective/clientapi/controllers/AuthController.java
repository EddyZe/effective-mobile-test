package ru.effective.clientapi.controllers;


import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("login")
    @Operation(summary = "Авторизация пользователя", description = "Возвращает jwt токен")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody @Valid UserSingInDTO userSingInDTO, BindingResult bindingResult) {
        log.info("created jwt token");
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
