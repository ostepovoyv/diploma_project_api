package in.reqres.tests;

import in.reqres.data.model.*;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.util.List;
import java.util.stream.Collectors;
import static in.reqres.config.ProjectConfig.PROPS;
import static in.reqres.data.Data.*;
import static in.reqres.data.Endpoints.*;
import static in.reqres.spec.Specifications.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;

@Epic("API Reqres")
@Owner("ostepovoyv")
public class TestReqres {

    @Test
    @Feature("Registration")
    @DisplayName("Успешная регистрация")
    public void successRegTest() {
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
    }

    @Test
    @Feature("Registration")
    @DisplayName("Регистрация без пароля")
    public void unSuccessRegTest() {
        RegistrData user = new RegistrData(USER_FOR_UN_SUCCESS_REG, PASS_FOR_UN_SUCCESS_REG);
        UnSuccessRegData unSuccessRegData = given()
                .spec(requestSpec(BASE_URL))
                .body(user)
                .post(API_REGISTER)
                .then().log().all()
                .spec(responseSpec(400))
                .extract().as(UnSuccessRegData.class);
        Assertions.assertEquals("Missing password", unSuccessRegData.getError());
    }

    @Test
    @Feature("Sorting")
    @DisplayName("Проверка ответа на наличие сортировки по годам")
    public void checkSortedYearsTest() {
        List<ColorsData> colorsData = given()
                .spec(requestSpec(BASE_URL))
                .when()
                .get(API_UNKNOWN)
                .then().log().all()
                .spec(responseSpec(200))
                .extract().body().jsonPath().getList("data", ColorsData.class);
        List<Integer> years = colorsData.stream().map(ColorsData::getYear).collect(Collectors.toList()); //Получаем список
        List<Integer> sortedYears = years.stream().sorted().collect(Collectors.toList()); // сортируем список для проверки
        Assertions.assertEquals(sortedYears, years);
        System.out.println(years);
        System.out.println(sortedYears);
    }


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

    @Test
    @Feature("CheckEmail")
    @DisplayName("Проверка email пользователей на содержание reqres.in и проверка наличия avatar")
    public void checkAvatarAndIdTest() {
        List<UserData> users = given()
                .spec(requestSpec(BASE_URL))
                .when()
                .get(API_LIST_USERS)
                .then().log().all()
                .spec(responseSpec(200))
                .extract().body().jsonPath().getList("data", UserData.class);
        users.forEach(x -> Assertions.assertTrue(x.getAvatar().contains(x.getId().toString())));
        Assertions.assertTrue(users.stream().allMatch(x -> x.getEmail().endsWith("@reqres.in")));
    }

    @Test
    @Feature("CheckEmail")
    @DisplayName("Проверка email при помощи groovy")
    public void checkEmailTest() {
        given()
                .spec(requestSpec(BASE_URL))
                .when()
                .get(API_USERS)
                .then().log().all()
                .spec(responseSpec(200))
                .body("data.findAll{it.email =~/.*?@reqres.in/}.email.flatten()",
                        hasItem("emma.wong@reqres.in"));
    }

    @Test
    @Feature("CheckTime")
    @DisplayName("Сравнение даты в ответе и текущей даты")
    public void checkServerAndPcDateTest(){
        UserInfo userTimeData = new UserInfo(USER_FOR_UPDATE_NAME, USER_FOR_UPDATE_JOB);
        UserResponse userTimeResponse = given()
                .spec(requestSpec(BASE_URL))
                .body(userTimeData)
                .when()
                .put(API_USERS + 2)
                .then().log().all()
                .spec(responseSpec(200))
                .extract().as(UserResponse.class);
        String regexForResponseTime = "(.{14})$";
        String regexForLocalTime = "(.{20})$";
        String currentLocalTime = Clock.systemUTC().instant().toString().replaceAll(regexForLocalTime, "");
        String responseTime = userTimeResponse.getUpdatedAt().replaceAll(regexForResponseTime,"");
        System.out.println(currentLocalTime);
        System.out.println(responseTime);
        Assertions.assertEquals(responseTime, currentLocalTime);
    }


}
