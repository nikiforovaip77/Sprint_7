package ru.praktikum.qa_scooter.test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.praktikum.qa_scooter.base.BaseTest;
import ru.praktikum.qa_scooter.config.Config;
import ru.praktikum.qa_scooter.data.Courier;
import ru.praktikum.qa_scooter.data.Login;
import ru.praktikum.qa_scooter.utils.CourierUtils;
import ru.praktikum.qa_scooter.utils.ResponseSteps;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;

@DisplayName("Тесты логина курьера")
public class LoginCourierTest extends BaseTest {

    private Courier courier;

    @BeforeEach
    void createCourier() {
        courier = CourierUtils.generateCourier("1234", "Test");

        given()
                .contentType(ContentType.JSON)
                .body(courier)
                .post(Config.COURIER_ENDPOINT);
    }

    @Test
    @DisplayName("Курьер может авторизоваться")
    void shouldLoginCourier() {
        Response response =
                CourierUtils.loginCourier(courier.getLogin(), courier.getPassword());

        ResponseSteps.checkStatusCode(response, 200);
        ResponseSteps.checkFieldExists(response, "id");
    }

    @Test
    @DisplayName("Ошибка при неверном пароле")
    void shouldReturnErrorForWrongPassword() {
        Response response =
                CourierUtils.loginCourier(courier.getLogin(), "wrong");

        ResponseSteps.checkStatusCode(response, 404);
        ResponseSteps.checkBody(response, "message", "Учетная запись не найдена");
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidLogins")
    @DisplayName("Ошибка при отсутствии обязательного поля ")
    void shouldReturnErrorIfRequiredFieldMissing(Login login) {

        Response response = given()
                .contentType(ContentType.JSON)
                .body(login)
                .post(Config.LOGIN_ENDPOINT);

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
        Response response =
                CourierUtils.loginCourier("unknown_login", "1234");

        ResponseSteps.checkStatusCode(response, 404);
        ResponseSteps.checkBody(response, "message", "Учетная запись не найдена");
    }

    @AfterEach
    void tearDown() {
        Response loginResponse =
                CourierUtils.loginCourier(courier.getLogin(), courier.getPassword());

        if (loginResponse.statusCode() == 200) {
            int id = loginResponse.jsonPath().getInt("id");
            CourierUtils.deleteCourier(id);
        }
    }
}