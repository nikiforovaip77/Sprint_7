package ru.praktikum.qa_scooter.utils;

import io.qameta.allure.Step;
import ru.praktikum.qa_scooter.data.Courier;

import java.util.UUID;

public class CourierGenerator {

    @Step("Генерация данных курьера")
    public static Courier generateCourier(String password, String firstName) {
        String login = "Nikiforova_" + UUID.randomUUID();
        return new Courier(login, password, firstName);
    }

}