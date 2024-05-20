package ru.effective.support.services;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.effective.commons.DTO.UserDTO;
import ru.effective.commons.entities.EmailAddress;
import ru.effective.commons.entities.PhoneNumber;
import ru.effective.commons.entities.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Service
@RequiredArgsConstructor
public class CreateUserService {

    private final UserService userService;
    private final PhoneNumberService phoneNumberService;
    private final EmailAddressService emailAddressService;
    private final PasswordEncoder encoder;


    @Transactional
    public void create(UserDTO userDTO) {
        User user = userService.save(convertToUser(userDTO));
        emailAddressService.save(createEmail(user, userDTO.getEmail()));
        phoneNumberService.save(createPhoneNumber(user, userDTO.getPhoneNumber()));
    }

    private User convertToUser(UserDTO userDTO) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return User.builder()
                .bankAccount(userDTO.getStartAmount())
                .startAmount(userDTO.getStartAmount())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .middleName(userDTO.getMiddleName())
                .username(userDTO.getUsername())
                .password(encoder.encode(userDTO.getPassword()))
                .birthDay(LocalDate.from(dtf.parse(userDTO.getBirthDay())))
                .build();
    }

    private EmailAddress createEmail(User user, String email) {
        return EmailAddress.builder()
                .email(email)
                .user(user)
                .build();
    }

    private PhoneNumber createPhoneNumber(User user, String phoneNumber) {
        return PhoneNumber.builder()
                .number(phoneNumber)
                .user(user)
                .build();
    }
}
