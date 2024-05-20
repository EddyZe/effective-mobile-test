package ru.effective.support.services;


import ru.effective.commons.entities.EmailAddress;
import ru.effective.commons.exceptions.EmailIsAlreadyException;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.effective.support.repositories.EmailRepository;

@Service
@RequiredArgsConstructor
public class EmailAddressService {

    private final EmailRepository emailRepository;


    @Transactional
    @CacheEvict(value = "Email", key = "#emailAddress.email")
    public void save(EmailAddress emailAddress) {
        if (emailRepository.findByEmail(emailAddress.getEmail()).isPresent())
            throw new EmailIsAlreadyException("Email занят!");

        emailRepository.save(emailAddress);
    }
}
