package in.reqres.tests;

import in.reqres.data.model.*;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static in.reqres.data.Data.*;
import static in.reqres.data.Endpoints.*;
import static in.reqres.spec.Specifications.requestSpec;
import static in.reqres.spec.Specifications.responseSpec;
import static io.restassured.RestAssured.given;


@Epic("reqres.in")
@Owner("ostepovoyv")
@DisplayName("Тесты с действиями над пользователями")
public class UserTest {

    @Test
    @Feature("Update")
    @DisplayName("Обновление пользователя")
    public void updateUser() {
        UserInfo updateUserInfo = new UserInfo(USER_FOR_UPDATE_NAME, USER_FOR_UPDATE_JOB);
        UserResponse updatedUserResponse = given()
                .spec(requestSpec(BASE_URL))
                .body(updateUserInfo).log().all()
                .when()
                .put(API_USERS + 2)
                .then().log().all()
                .spec(responseSpec(200))
                .extract().as(UserResponse.class);
        Assertions.assertEquals(updateUserInfo.getName(), updatedUserResponse.getName());
        Assertions.assertEquals(updateUserInfo.getJob(), updatedUserResponse.getJob());
        Assertions.assertNotNull(updatedUserResponse.getUpdatedAt());
    }

    @Test
    @Feature("Delete")
    @DisplayName("Проверка статус кода после удаления")
    public void deleteUserTest() {
        given()
                .spec(requestSpec(BASE_URL))
                .when()
                .delete(API_USERS + USER_FOR_DELETE)
                .then().log().all()
                .spec(responseSpec(204));
    }

}
