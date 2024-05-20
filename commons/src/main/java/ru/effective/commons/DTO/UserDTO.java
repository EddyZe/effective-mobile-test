package ru.effective.commons.DTO;




import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO {

    @JsonProperty("username")
    @NotEmpty(message = "Логин не должен быть пустым!")
    private String username;

    @JsonProperty("password")
    @NotEmpty(message = "Пароль не может быть пустым!")
    @Size(min = 6, message = "Пароль не должен быть короче чем 6 символов")
    private String password;

    @JsonProperty("email")
    @Email(message = "Не верный email!")
    @NotEmpty(message = "Email не может быть пустым!")
    private String email;

    @JsonProperty("first_name")
    @NotEmpty(message = "Имя не может быть пустым!")
    private String firstName;

    @JsonProperty("last_name")
    @NotEmpty(message = "Фамилия не может быть пустой!")
    private String lastName;

    @JsonProperty("middle_name")
    @NotEmpty(message = "Отчество не может быть пустым!")
    private String middleName;

    @JsonProperty("phone_number")
    @NotEmpty(message = "Укажите номер телефона!")
    @Pattern(regexp = "^8\\d{10}", message = "Номер должен быть в формате: 89998887766")
    private String phoneNumber;

    @JsonProperty("start_amount")
    @NotNull(message = "Укажите начальную сумму!")
    @Min(value = 0, message = "Сумма не должна быть меньше чем 0")
    private Long startAmount;

    @JsonProperty("birth_day")
    @NotEmpty(message = "Введите дату рождения!")
    @Pattern(regexp = "\\d{2}.\\d{2}.\\d{4}", message = "Введите дату в формате: дд.мм.гггг")
    private String birthDay;
}
