package ru.praktikum.qa_scooter.data;

import lombok.AllArgsConstructor;
import lombok.Data;

// Класс для логина курьером
@Data
@AllArgsConstructor
public class Login {

    private String login;
    private String password;

}
