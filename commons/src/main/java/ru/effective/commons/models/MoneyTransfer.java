package ru.effective.commons.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MoneyTransfer {

    private String username;

    @JsonProperty("phone_number")
    @Pattern(regexp = "^8\\d{10}", message = "Номер должен быть в формате: 89998887766")
    private String phoneNumber;

    private String email;


    @NotNull(message = "Введите сумму перевода!")
    private Double amount;
}
