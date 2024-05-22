package ru.effective.commons.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PhoneNumberDTO{

    @JsonProperty("phone_number")
    @NotEmpty(message = "Введите номер телефона!")
    @Pattern(regexp = "^8\\d{10}", message = "Номер должен быть в формате: 89998887766")
    private String phoneNumber;

}
