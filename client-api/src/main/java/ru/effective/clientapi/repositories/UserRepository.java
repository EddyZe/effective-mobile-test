package ru.effective.clientapi.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.effective.commons.entities.User;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
