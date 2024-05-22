package ru.effective.clientapi.services;


import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.effective.clientapi.repositories.EmailRepository;
import ru.effective.commons.entities.EmailAddress;
import ru.effective.commons.exceptions.EmailAddressNotFoundException;
import ru.effective.commons.exceptions.EmailIsAlreadyException;

import java.util.List;
import java.util.Optional;


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


    @Cacheable(value = "Email", key = "#email")
    public Optional<EmailAddress> findByEmail(String email) {
        return emailRepository.findByEmail(email);
    }

    public List<EmailAddress> findByUserUsername(String username) {
        return emailRepository.findByUserUsername(username);
    }


    @Transactional
    @CacheEvict(value = "Email", key = "#emailAddress.email")
    public void delete(EmailAddress emailAddress) {
        if (emailRepository.findByEmail(emailAddress.getEmail()).isEmpty())
            throw new EmailAddressNotFoundException("Email не найден");
        emailRepository.delete(emailAddress);
    }
}
