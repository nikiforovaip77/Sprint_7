package ru.praktikum.qa_scooter.data;

import lombok.AllArgsConstructor;
import lombok.Data;

// Класс для создания курьера
@Data
@AllArgsConstructor
public class Courier {

    private String login;
    private String password;
    private String firstName;

}
