package ru.effective.clientapi.services;


import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.effective.clientapi.repositories.UserRepository;
import ru.effective.commons.entities.User;
import ru.effective.commons.exceptions.UsernameIsAlreadyException;

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


    @Cacheable(value = "User", key = "#username")
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @CacheEvict(value = "User", key = "#user.username")
    public void update(User user) {
        userRepository.save(user);
    }

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }
}
