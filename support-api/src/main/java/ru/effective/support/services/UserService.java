package ru.effective.support.services;


import ru.effective.commons.exceptions.UsernameIsAlreadyException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.effective.support.repositories.UserRepository;
import ru.effective.commons.entities.User;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    @CacheEvict(value = "User", key = "#user.username")
    public User save (User user) {

        Optional<User> userOptional =
                userRepository.findByUsername(user.getUsername());

        if (userOptional.isPresent())
            throw new UsernameIsAlreadyException("Такой логин уже занят!");

        return userRepository.save(user);
    }


}
