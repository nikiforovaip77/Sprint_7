package ru.praktikum.qa_scooter.base;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import ru.praktikum.qa_scooter.config.Config;

public abstract class BaseTest {

    static {
        RestAssured.baseURI = Config.BASE_URL;
        RestAssured.filters(new AllureRestAssured());
    }
}