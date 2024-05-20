package ru.effective.support.repositories;


import ru.effective.commons.entities.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Integer> {
    Optional<PhoneNumber> findByNumber(String phoneNumber);
}
