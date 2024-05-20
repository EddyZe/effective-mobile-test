package ru.effective.commons.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class UserSingInDTO {

    @NotEmpty(message = "Логин не должен быть пустым")
    private String username;

    @NotEmpty(message = "Пароль не должен быть пустым")
    private String password;
}
