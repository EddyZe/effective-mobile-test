package ru.effective.commons.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "phone_number")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PhoneNumber implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "Номер не может быть пустым!")
    @Pattern(regexp = "^8\\d{10}", message = "Номер должен быть в формате: 89998887766")
    private String number;


    @NotNull(message = "У номера должен быть владелец!")
    @ManyToOne
    @JoinColumn(name = "owner_phone_number", referencedColumnName = "id")
    private User user;
}
