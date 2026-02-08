package ru.praktikum.qa_scooter;

import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.praktikum.qa_scooter.config.Config;
import ru.praktikum.qa_scooter.utils.ResponseSteps;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@Feature("Получение списка заказов")
@DisplayName("Тесты получения списка заказов")
public class GetOrdersListTest {

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = Config.BASE_URL;
    }


    @Test
    @DisplayName("Получение списка заказов")
    void shouldReturnOrdersList() {
        Response response = getOrders();

        ResponseSteps.checkStatusCode(response, 200);

        response.then().body("orders", notNullValue());
    }

    @Step("Получение списка заказов")
    private Response getOrders() {
        return given()
                .get(Config.ORDERS_ENDPOINT);
    }
}