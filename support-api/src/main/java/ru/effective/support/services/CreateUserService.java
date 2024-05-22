package ru.effective.support.services;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.effective.commons.DTO.CreateUserDTO;
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
    public void create(CreateUserDTO createUserDTO) {
        User user = userService.save(convertToUser(createUserDTO));
        emailAddressService.save(createEmail(user, createUserDTO.getEmail()));
        phoneNumberService.save(createPhoneNumber(user, createUserDTO.getPhoneNumber()));
    }

    private User convertToUser(CreateUserDTO createUserDTO) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate birthDay = null;
        if (createUserDTO.getBirthDay() != null)
            birthDay = LocalDate.from(dtf.parse(createUserDTO.getBirthDay()));

        return User.builder()
                .bankAccount(createUserDTO.getStartAmount())
                .startAmount(createUserDTO.getStartAmount())
                .firstName(createUserDTO.getFirstName())
                .lastName(createUserDTO.getLastName())
                .middleName(createUserDTO.getMiddleName())
                .username(createUserDTO.getUsername())
                .password(encoder.encode(createUserDTO.getPassword()))
                .birthDay(birthDay)
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
