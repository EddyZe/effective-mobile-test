package ru.effective.clientapi.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.effective.commons.entities.EmailAddress;


import java.util.List;
import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<EmailAddress, Integer> {

    Optional<EmailAddress> findByEmail(String email);

    List<EmailAddress> findByUserUsername(String username);
}
