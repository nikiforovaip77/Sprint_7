package ru.praktikum.qa_scooter.api;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.praktikum.qa_scooter.config.Config;
import ru.praktikum.qa_scooter.data.Courier;
import ru.praktikum.qa_scooter.data.Login;

import static io.restassured.RestAssured.given;


//HTTP-запросы: создание курьера, логин курьером, удаление курьера
public class CourierApi {

    @Step("Создание курьера")
    public static Response createCourier(Courier courier) {
        return given()
                .contentType(ContentType.JSON)
                .body(courier)
                .post(Config.COURIER_ENDPOINT);
    }

    @Step("Авторизация курьером и получение id")
    public static Response loginCourier(Login login) {
        return given()
                .contentType(ContentType.JSON)
                .body(login)
                .post(Config.LOGIN_ENDPOINT);
    }

    @Step("Удаление курьера с id {id}")
    public static Response deleteCourier(int id) {
        return given()
                .delete(Config.COURIER_ENDPOINT + "/" + id);
    }
}
