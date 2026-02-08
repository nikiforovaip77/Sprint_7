package ru.praktikum.qa_scooter;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.praktikum.qa_scooter.base.BaseTest;
import ru.praktikum.qa_scooter.config.Config;
import ru.praktikum.qa_scooter.utils.ResponseSteps;

import java.util.List;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Тесты создания заказа")
public class CreateOrderTest extends BaseTest {

    @Step("Создание заказа")
    private Response createOrder(Order order) {
        return given()
                .contentType("application/json")
                .body(order)
                .post(Config.ORDERS_ENDPOINT);
    }


    @ParameterizedTest(name = "{0}")
    @MethodSource("colors")
    @DisplayName("Создание заказа с разными вариантами цвета")
    void shouldCreateOrderWithDifferentColors(List<String> colors) {
        Order order = new Order(
                "Test",
                "User",
                "Moscow",
                "4",
                "+79999999999",
                3,
                "2026-02-07",
                "Test order",
                colors
        );

        Response response = createOrder(order);

        ResponseSteps.checkStatusCode(response, 201);
        response.then().body("track", notNullValue());
    }

    // Тестовые наборы с разными цветами
    static Stream<Arguments> colors() {
        return Stream.of (
                Arguments.of(
                        Named.of("[BLACK]", List.of("BLACK"))
                ),
                Arguments.of(
                        Named.of("[GREY]", List.of("GREY"))
                ),
                Arguments.of(
                        Named.of("[BLACK, GREY]", List.of("BLACK", "GREY"))
                ) ,
                Arguments.of(
                        Named.of("[NULL]", null)
                )
        );
    }
}