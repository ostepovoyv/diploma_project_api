package in.reqres.tests;

import in.reqres.data.model.*;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static in.reqres.config.ProjectConfig.PROPS;
import static in.reqres.data.Data.*;
import static in.reqres.data.Endpoints.*;
import static in.reqres.spec.Specifications.requestSpec;
import static in.reqres.spec.Specifications.responseSpec;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;


@Epic("reqres.in")
@Owner("ostepovoyv")
@Feature("Registration")
@DisplayName("Тесты на регистрацию")
public class RegistrationTests {

    @Test
    @DisplayName("Успешная регистрация")
    public void successRegTest() {
        step("Тестируем регистрацию пользователя", () -> {
            RegistrData user = new RegistrData(PROPS.getEmail(), PROPS.getPassword());
            SuccessRegData successReg = given()
                    .spec(requestSpec(BASE_URL))
                    .body(user)
                    .when()
                    .post(API_REGISTER)
                    .then().log().all()
                    .spec(responseSpec(200))
                    .extract().as(SuccessRegData.class);
            Assertions.assertNotNull(successReg.getId());
            Assertions.assertNotNull(successReg.getToken());
            Assertions.assertEquals(ID, successReg.getId());
            Assertions.assertEquals(PROPS.getToken(), successReg.getToken());
        });
    }

    @Test
    @DisplayName("Регистрация без пароля")
    public void unSuccessRegTest() {
        step("Тестируем регистрацию без пароля", () -> {
            RegistrData user = new RegistrData(USER_FOR_UN_SUCCESS_REG, PASS_FOR_UN_SUCCESS_REG);
            UnSuccessRegData unSuccessRegData = given()
                    .spec(requestSpec(BASE_URL))
                    .body(user)
                    .post(API_REGISTER)
                    .then().log().all()
                    .spec(responseSpec(400))
                    .extract().as(UnSuccessRegData.class);
            Assertions.assertEquals("Missing password", unSuccessRegData.getError());
        });
    }

}
