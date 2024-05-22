package ru.effective.clientapi.services;


import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.effective.clientapi.repositories.UserRepository;
import ru.effective.commons.entities.User;
import ru.effective.commons.exceptions.UsernameIsAlreadyException;
import ru.effective.commons.models.MoneyTransfer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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

    public List<User> findByBirthDayAfterAndName(LocalDate birthDay,
                                          String firstName,
                                          String lastName,
                                          String middleName) {
        return userRepository.findByBirthDayAfterAndName(birthDay, firstName, lastName, middleName);
    }


    @Transactional
    @Scheduled(cron = "0 * * * * *")
    public void IncreasingTheBalance() {
        List<User> users = userRepository.findAll();
        users.forEach(user -> {
            Double maxBalance = user.getStartAmount() + ((user.getStartAmount() * 207) / 100);
            if (user.getBankAccount() < maxBalance) {
                Double newBalance = user.getBankAccount() + ((user.getBankAccount() * 5) / 100);
                user.setBankAccount(newBalance);
                update(user);
            }
        });
    }
}
