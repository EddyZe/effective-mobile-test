package ru.effective.clientapi.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.effective.clientapi.services.EmailAddressService;
import ru.effective.clientapi.services.PhoneNumberService;
import ru.effective.clientapi.services.UserService;
import ru.effective.commons.DTO.EmailAddressDTO;
import ru.effective.commons.DTO.PhoneNumberDTO;
import ru.effective.commons.DTO.UserDTO;
import ru.effective.commons.entities.User;
import ru.effective.commons.exceptions.UserInvalidException;
import ru.effective.commons.exceptions.UserNotFoundException;
import ru.effective.commons.models.Search;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PhoneNumberService phoneNumberService;
    private final EmailAddressService emailAddressService;

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");


    @GetMapping("search")
    public ResponseEntity<List<UserDTO>> search(@RequestParam(value = "firstname", required = false) String firstname,
                                                @RequestParam(value = "lastname", required = false) String lastname,
                                                @RequestParam(value = "middle-name", required = false) String middleName,
                                                @RequestBody(required = false) Search search) {

        LocalDate birthDay = LocalDate.from(dateTimeFormatter.parse("01.01.1900"));

        if (search != null) {
            if (search.getPhoneNumber() != null && search.getEmail() != null) {
                return findByEmailAndPhoneNumber(search);
            }

            if (search.getEmail() != null) {
                return findByEmail(search);
            }

            if (search.getPhoneNumber() != null) {
                return findByPhoneNumber(search);
            }


            if (search.getBirthDay() != null) {
                if (!search.getBirthDay().matches("\\d{2}\\.\\d{2}\\.\\d{4}"))
                    throw new UserInvalidException("Введите birth_day в формате дд.мм.гггг");

                birthDay = LocalDate.from(dateTimeFormatter.parse(search.getBirthDay()));
            }
        }

        return new ResponseEntity<>(
                userService.findByBirthDayAfterAndName(birthDay, firstname, lastname, middleName)
                        .stream()
                        .map(this::convertToUserDTO)
                        .toList(),
                HttpStatus.OK);
    }

    private ResponseEntity<List<UserDTO>> findByPhoneNumber(Search search) {
        User user = phoneNumberService.findByNumber(search.getPhoneNumber())
                .orElseThrow(() -> new UserNotFoundException("Пользователь с таким номером не найден!"))
                .getUser();

        return new ResponseEntity<>(Collections.singletonList(convertToUserDTO(user)), HttpStatus.OK);
    }

    private ResponseEntity<List<UserDTO>> findByEmail(Search search) {
        User user = emailAddressService.findByEmail(search.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Пользователь с таким email не найден!"))
                .getUser();

        return new ResponseEntity<>(Collections.singletonList(convertToUserDTO(user)), HttpStatus.OK);
    }

    private ResponseEntity<List<UserDTO>> findByEmailAndPhoneNumber(Search search) {
        User ownerPhone = phoneNumberService.findByNumber(search.getPhoneNumber())
                .orElseThrow(() -> new UserNotFoundException("Пользователь с таким номером телефоном не найден!"))
                .getUser();

        String ownerEmailUsername = emailAddressService.findByEmail(search.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Пользователь с таким email и номером телефона не найден!"))
                .getUser().getUsername();

        if (!ownerEmailUsername.equals(ownerPhone.getUsername()))
            throw new UserNotFoundException("Пользователь с таким email и номером телефона не найден!");

        return new ResponseEntity<>(Collections.singletonList(convertToUserDTO(ownerPhone)), HttpStatus.OK);
    }

    private UserDTO convertToUserDTO(User user) {
        List<EmailAddressDTO> emails = emailAddressService.findByUserUsername(user.getUsername())
                .stream().map(emailAddress -> new EmailAddressDTO(emailAddress.getEmail())).toList();

        List<PhoneNumberDTO> phoneNumbers = phoneNumberService.findByUserUsername(user.getUsername())
                .stream().map(phoneNumber -> new PhoneNumberDTO(phoneNumber.getNumber())).toList();

        String birthDay = null;
        if (user.getBirthDay() != null)
            birthDay = dateTimeFormatter.format(user.getBirthDay());

        return UserDTO.builder()
                .birthDay(birthDay)
                .username(user.getUsername())
                .lastName(user.getLastName())
                .middleName(user.getMiddleName())
                .emails(emails)
                .phoneNumbers(phoneNumbers)
                .firstName(user.getFirstName())
                .startAmount(user.getStartAmount())
                .balance(user.getBankAccount())
                .build();
    }
}
