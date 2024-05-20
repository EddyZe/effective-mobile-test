package ru.effective.commons.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "email")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EmailAddress implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Email(message = "Не верный email!")
    @NotEmpty(message = "Email не может быть пустым!")
    @Column(name = "email")
    private String email;

    @NotNull(message = "У email должен быть владелец!")
    @ManyToOne
    @JoinColumn(name = "owner_email", referencedColumnName = "id")
    private User user;
}
