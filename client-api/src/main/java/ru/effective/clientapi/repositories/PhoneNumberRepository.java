package ru.effective.clientapi.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.effective.commons.entities.PhoneNumber;

import java.util.Optional;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Integer> {
    Optional<PhoneNumber> findByNumber(String phoneNumber);
}
