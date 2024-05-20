package ru.effective.clientapi.services;


import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.effective.clientapi.repositories.PhoneNumberRepository;
import ru.effective.commons.exceptions.PhoneNumberIsAlreadyException;
import ru.effective.commons.entities.PhoneNumber;

import java.util.Optional;

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


    @Cacheable(value = "PhoneNumber", key = "#phoneNumber")
    public Optional<PhoneNumber> findByNumber(String phoneNumber) {
        return phoneNumberRepository.findByNumber(phoneNumber);
    }
}
