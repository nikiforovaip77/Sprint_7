package ru.praktikum.qa_scooter.test;

import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.praktikum.qa_scooter.api.CourierApi;
import ru.praktikum.qa_scooter.base.BaseTest;
import ru.praktikum.qa_scooter.data.Courier;
import ru.praktikum.qa_scooter.data.Login;
import ru.praktikum.qa_scooter.utils.CourierGenerator;
import ru.praktikum.qa_scooter.utils.ResponseSteps;

import java.util.stream.Stream;

@DisplayName("Тесты логина курьера")
public class LoginCourierTest extends BaseTest {

    private Courier courier;
    private Integer courierId;

    @Test
    @DisplayName("Успешная авторизация курьера")
    void shouldLoginCourier() {
        courier = CourierGenerator.generateCourier("1234", "Test");
        CourierApi.createCourier(courier);
        Login login = new Login(courier.getLogin(), courier.getPassword());
        Response response = CourierApi.loginCourier(login);

        ResponseSteps.checkStatusCode(response, 200);
        ResponseSteps.checkFieldExists(response, "id");

        courierId = response.jsonPath().getInt("id");
    }

    @Test
    @DisplayName("Ошибка при неверном пароле")
    void shouldReturnErrorForWrongPassword() {
        courier = CourierGenerator.generateCourier("1234", "Test");
        CourierApi.createCourier(courier);
        Login login = new Login(courier.getLogin(), "wrong");
        Response response = CourierApi.loginCourier(login);

        ResponseSteps.checkStatusCode(response, 404);
        ResponseSteps.checkBody(response, "message", "Учетная запись не найдена");
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidLogins")
    @DisplayName("Ошибка при отсутствии обязательного поля ")
    void shouldReturnErrorIfRequiredFieldMissing(Login login) {
        Response response = CourierApi.loginCourier(login);

        ResponseSteps.checkStatusCode(response, 400);
        ResponseSteps.checkBody(response, "message", "Недостаточно данных для входа");
    }

    // Тестовые данные отсутствия логина или пароля
    static Stream<Arguments> invalidLogins() {
        return Stream.of(
                Arguments.of(
                        Named.of("[без login]", new Login(null, "1234"))
                ),
                Arguments.of(
                        Named.of("[без password]", new Login("login", null))
                )
        );
    }

    @Test
    @DisplayName("Ошибка при несуществующем курьере")
    void shouldReturnErrorForNonExistentCourier() {
        Login login = new Login("unknown_login", "1234");
        Response response = CourierApi.loginCourier(login);

        ResponseSteps.checkStatusCode(response, 404);
        ResponseSteps.checkBody(response, "message", "Учетная запись не найдена");
    }

    @AfterEach
    void tearDown() {
        if (courierId != null) {
            CourierApi.deleteCourier(courierId);
        }
    }
}