package ru.effective.clientapi.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.effective.clientapi.services.UserService;
import ru.effective.commons.entities.User;
import ru.effective.commons.exceptions.UserNotFoundException;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {
    private final UserService userService;

    @GetMapping("getUser/{id}")
    public User getUser(@PathVariable("id") Integer id) {
        return userService.findById(id).orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
    }
}
