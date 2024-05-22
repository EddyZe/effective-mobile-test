package ru.effective.commons.DTO;




import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;
import ru.effective.commons.entities.PhoneNumber;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO {

    @JsonProperty("username")
    private String username;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("middle_name")
    private String middleName;

    @JsonProperty("birth_day")
    private String birthDay;

    @JsonProperty("start_amount")
    private Double startAmount;

    @JsonProperty("balance")
    private Double balance;

    @JsonProperty("emails")
    private List<EmailAddressDTO> emails;

    @JsonProperty("phone_numbers")
    private List<PhoneNumberDTO> phoneNumbers;

}
