package ru.praktikum.qa_scooter.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikum.qa_scooter.config.Config;
import ru.praktikum.qa_scooter.data.Order;

import static io.restassured.RestAssured.given;

//HTTP-запросы: создание заказа, запрос списка заказов
public class OrderApi {

    @Step("Создание заказа")
    public static Response createOrder(Order order) {
        return given()
                .contentType("application/json")
                .body(order)
                .post(Config.ORDERS_ENDPOINT);
    }

    @Step("Получение списка заказов")
    public static Response getOrders() {
        return given()
                .get(Config.ORDERS_ENDPOINT);
    }
}
