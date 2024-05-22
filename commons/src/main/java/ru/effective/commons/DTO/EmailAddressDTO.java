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
public class EmailAddressDTO {

    @JsonProperty("email")
    @NotEmpty(message = "Введите email!")
    private String email;
}
