package ru.effective.support.services;


import ru.effective.commons.entities.PhoneNumber;
import ru.effective.commons.exceptions.PhoneNumberIsAlreadyException;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.effective.support.repositories.PhoneNumberRepository;

@Service
@RequiredArgsConstructor
public class PhoneNumberService {

    private final PhoneNumberRepository phoneNumberRepository;



    @Transactional
    @CacheEvict(value = "PhoneNumber", key = "#phoneNumber.number")
    public void save(PhoneNumber phoneNumber) {
        if (phoneNumberRepository.findByNumber(phoneNumber.getNumber()).isPresent())
            throw new PhoneNumberIsAlreadyException("Такой номер телефона занят!");
        phoneNumberRepository.save(phoneNumber);
    }
}
