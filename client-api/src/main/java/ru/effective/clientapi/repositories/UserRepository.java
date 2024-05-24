package ru.effective.clientapi.repositories;


import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.effective.commons.entities.User;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);


    @Query("select u from User u where (:birthDay is null or u.birthDay > :birthDay) " +
           "and (:#{#firstname} is null or u.firstName like %:#{#firstname}%)" +
           "and(:#{#lastname} is null or u.lastName like %:#{#lastname}%)" +
           "and (:#{#middleName} is null or u.middleName like %:#{#middleName}%)")
    List<User> findByBirthDayAfterAndName(@Param("birthDay") LocalDate birthDay,
                                   @Param("firstname") String firstname,
                                   @Param("lastname") String lastname,
                                   @Param("middleName") String middleName);

}
