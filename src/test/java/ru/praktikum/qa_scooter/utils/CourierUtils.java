package ru.praktikum.qa_scooter.utils;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.praktikum.qa_scooter.data.Courier;
import ru.praktikum.qa_scooter.data.Login;
import ru.praktikum.qa_scooter.config.Config;

import java.util.UUID;

import static io.restassured.RestAssured.given;

public class CourierUtils {

    @Step("Сгенерировать данные курьера")
    public static Courier generateCourier(String password, String firstName) {
        String login = "Nikiforova_" + UUID.randomUUID();
        return new Courier(login, password, firstName);
    }

    @Step("Авторизоваться курьером и получить id")
    public static Response loginCourier(String login, String password) {
        return given()
                .contentType(ContentType.JSON)
                .body(new Login(login, password))
                .post(Config.LOGIN_ENDPOINT);
    }

    @Step("Удалить курьера")
    public static Response deleteCourier(int id) {
        return given()
                .delete(Config.COURIER_ENDPOINT + "/" + id);
    }
}