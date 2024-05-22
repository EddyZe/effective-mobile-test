package ru.effective.support.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.effective.commons.DTO.CreateUserDTO;
import ru.effective.commons.exceptions.UserInvalidException;
import ru.effective.support.services.CreateUserService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("support-api")
public class CreateUserController {

    private final CreateUserService createUserService;

    @PostMapping("create-user")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid CreateUserDTO createUserDTO,
                                             BindingResult bindingResult) {
        log.info("Start creating a new user");

        if (createUserDTO.getBirthDay() != null)
            if (!createUserDTO.getBirthDay().matches("\\d{2}\\.\\d{2}\\.\\d{4}"))
                throw new UserInvalidException("Введите birth_day в формате дд.мм.гггг");

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

        createUserService.create(createUserDTO);
        log.info("A new user has been created");
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
}
