package ru.effective.clientapi.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.effective.clientapi.services.EmailAddressService;
import ru.effective.clientapi.services.PhoneNumberService;
import ru.effective.clientapi.services.UserService;
import ru.effective.commons.entities.User;
import ru.effective.commons.exceptions.MoneyTransferException;
import ru.effective.commons.exceptions.UserNotFoundException;
import ru.effective.commons.models.MoneyTransfer;

@RestController
@RequestMapping("transfer")
@RequiredArgsConstructor
@Slf4j
public class TransferMoneyController {

    private final UserService userService;
    private final PhoneNumberService phoneNumberService;
    private final EmailAddressService emailAddressService;

    @PostMapping("{username}/money-transfer")
    @Operation(summary = "Сделать перевод", description = "Можно сделать перевод указав номер, email, или username, указать нужно что-то одно.")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<HttpStatus> moneyTransfer(@PathVariable String username,
                                                    @RequestBody @Valid MoneyTransfer moneyTransfer,
                                                    BindingResult bindingResult) {
        log.info("%s start transfer money".formatted(username));
        handlerBindingResult(bindingResult);
        if (moneyTransfer.getPhoneNumber() == null && moneyTransfer.getEmail() == null && moneyTransfer.getUsername() == null)
            throw new MoneyTransferException("Введите username пользователя кому хотите сделать перевод, либо email, либо phone_number, чтобы сделать перевод!");


        User sender = userService.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Отправитель с username \"%s\" не найден".formatted(username)));

        Double amountTransfer = moneyTransfer.getAmount();

        if (moneyTransfer.getUsername() != null) {
            transferByUsername(sender, moneyTransfer, amountTransfer);
        } else if (moneyTransfer.getPhoneNumber() != null) {
            transferByPhoneNumber(moneyTransfer, sender, amountTransfer);
        } else if (moneyTransfer.getEmail() != null) {
            transferByEmailAddress(moneyTransfer, sender, amountTransfer);
        }

        log.info("%s transfer money completed".formatted(username));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private void transferByEmailAddress(MoneyTransfer moneyTransfer, User sender, Double amountTransfer) {
        String recipientUsername = emailAddressService.findByEmail(moneyTransfer.getEmail())
                .orElseThrow(() -> new MoneyTransferException("Пользователь с таким email не найден"))
                .getUser().getUsername();

        User recipient = userService.findByUsername(recipientUsername)
                .orElseThrow(() -> new MoneyTransferException("Получатель не найден!"));

        makeTransfer(sender, amountTransfer, recipient);
    }

    private void transferByPhoneNumber(MoneyTransfer moneyTransfer, User sender, Double amountTransfer) {
        String recipientUsername = phoneNumberService.findByNumber(moneyTransfer.getPhoneNumber())
                .orElseThrow(() -> new MoneyTransferException("Пользователь с таким номером телефона не найден"))
                .getUser().getUsername();

        User recipient = userService.findByUsername(recipientUsername)
                .orElseThrow(() -> new MoneyTransferException("Получатель не найден!"));

        makeTransfer(sender, amountTransfer, recipient);
    }

    private void transferByUsername(User sender, MoneyTransfer moneyTransfer, Double amountTransfer) {

        User recipient = userService.findByUsername(moneyTransfer.getUsername())
                .orElseThrow(() -> new UserNotFoundException("Получатель с таким username не найден"));

        makeTransfer(sender, amountTransfer, recipient);
    }

    private void makeTransfer(User sender, Double amountTransfer, User recipient) {
        if (sender.getBankAccount() - amountTransfer < 0)
            throw new MoneyTransferException("Не достаточно средств");

        if (sender.getUsername().equals(recipient.getUsername()))
            throw new MoneyTransferException("Вы не можете сделать перевод самому себе!");

        sender.setBankAccount(sender.getBankAccount() - amountTransfer);
        recipient.setBankAccount(recipient.getBankAccount() + amountTransfer);
        userService.update(sender);
        userService.update(recipient);
        log.info("The money transfer is completed");
    }

    private void handlerBindingResult(BindingResult bindingResult) {
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

            throw new MoneyTransferException(errorsMessage.toString());
        }
    }
}
