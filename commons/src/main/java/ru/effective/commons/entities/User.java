package ru.effective.commons.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @NotEmpty(message = "Логин не должен быть пустым!")
    @Column(name = "username")
    private String username;

    @Column(name = "birth_day")
    private LocalDate birthDay;

    @NotEmpty(message = "Пароль не может быть пустым")
    @Size(min = 6, message = "Пароль должен быть не менее 6 символов")
    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user")
    private List<EmailAddress> emailAddresses;

    @OneToMany(mappedBy = "user")
    private List<PhoneNumber> phoneNumbers;

    @Column(name = "bank_account")
    private Double bankAccount;

    @NotNull(message = "Укажите начальную сумму!")
    @Min(value = 0, message = "Начальная сумма не может быть меньше чем 0!")
    @Column(name = "start_amount")
    private Double startAmount;
}
