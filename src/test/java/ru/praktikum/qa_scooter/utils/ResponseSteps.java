package ru.praktikum.qa_scooter.utils;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;


public class ResponseSteps {

    @Step("Проверка кода ответа {expectedStatus}")
    public static void checkStatusCode(Response response, int expectedStatus) {
        response.then().statusCode(expectedStatus);
    }

    @Step("Проверка тела ответа: {path} = {expected}")
    public static void checkBody(Response response, String path, Object expected) {
        response.then().body(path, equalTo(expected));
    }

    @Step("Проверка, что поле {path} существует")
    public static void checkFieldExists(Response response, String path) {
        response.then().body(path, notNullValue());
    }

}