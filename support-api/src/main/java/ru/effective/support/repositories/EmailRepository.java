package ru.effective.support.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.effective.commons.entities.EmailAddress;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<EmailAddress, Integer> {

    Optional<EmailAddress> findByEmail(String email);
}
