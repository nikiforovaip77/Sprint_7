package ru.praktikum.qa_scooter;

import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.praktikum.qa_scooter.base.BaseTest;
import ru.praktikum.qa_scooter.config.Config;
import ru.praktikum.qa_scooter.utils.CourierUtils;
import ru.praktikum.qa_scooter.utils.ResponseSteps;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;

@Feature("Создание курьера")
@DisplayName("Тесты создания курьера")
public class CreateCourierTest extends BaseTest {

    private Courier courier;

    @Step("Создание курьера")
    private Response createCourier(Courier courier) {
        return given()
                .contentType(ContentType.JSON)
                .body(courier)
                .post(Config.COURIER_ENDPOINT);
    }

    @Test
    @DisplayName("Успешное создание курьера")
    void shouldCreateCourier() {
        courier = CourierUtils.generateCourier("1234", "Test");

        Response response = createCourier(courier);

        ResponseSteps.checkStatusCode(response, 201);
        ResponseSteps.checkBody(response, "ok", true);
    }

    @Test
    @DisplayName("Ошибка при создании курьера с существующим логином")
    void shouldNotCreateDuplicateCourier() {
        courier = CourierUtils.generateCourier("1234", "Test");

        createCourier(courier);
        Response response = createCourier(courier);

        ResponseSteps.checkStatusCode(response, 409);
        ResponseSteps.checkBody(response, "message", "Этот логин уже используется");
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidCouriers")
    @DisplayName("Ошибка при создании курьера ")
    void shouldReturnErrorIfRequiredFieldMissing(Courier courier) {
        Response response = createCourier(courier);

        ResponseSteps.checkStatusCode(response, 400);
        ResponseSteps.checkBody(
                response,
                "message",
                "Недостаточно данных для создания учетной записи"
        );
    }

    static Stream<Arguments> invalidCouriers() {
        return Stream.of(
                Arguments.of(
                        Named.of("[без login]", new Courier(null, "1234", "Test"))
                ),
                Arguments.of(
                        Named.of("[без password]", new Courier("login111", null, "Test"))
                )
        );
    }

    @AfterEach
    void tearDown() {
        if (courier != null) {
            Response loginResponse =
                    CourierUtils.loginCourier(courier.getLogin(), courier.getPassword());

            if (loginResponse.statusCode() == 200) {
                int id = loginResponse.jsonPath().getInt("id");
                CourierUtils.deleteCourier(id);
            }
        }
    }
}