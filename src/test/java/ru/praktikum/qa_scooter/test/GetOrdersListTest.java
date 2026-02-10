package ru.praktikum.qa_scooter.test;

import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.praktikum.qa_scooter.api.OrderApi;
import ru.praktikum.qa_scooter.base.BaseTest;
import ru.praktikum.qa_scooter.utils.ResponseSteps;

@Feature("Получение списка заказов")
@DisplayName("Тесты получения списка заказов")
public class GetOrdersListTest extends BaseTest {

    @Test
    @DisplayName("Получение списка заказов")
    @Step("Получение списка заказов")
    void shouldReturnOrdersList() {
        Response response = OrderApi.getOrders();

        ResponseSteps.checkStatusCode(response, 200);
        ResponseSteps.checkFieldExists(response,"orders");
    }

}