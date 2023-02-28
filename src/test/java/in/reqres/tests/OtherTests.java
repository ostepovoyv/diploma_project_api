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

import static in.reqres.data.Data.*;
import static in.reqres.data.Endpoints.*;
import static in.reqres.spec.Specifications.requestSpec;
import static in.reqres.spec.Specifications.responseSpec;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;

@Epic("reqres.in")
@Owner("ostepovoyv")
@DisplayName("Прочие проверки reqres.in")
public class OtherTests {

    @Test
    @Feature("Sorting")
    @DisplayName("Проверка ответа на наличие сортировки по годам")
    public void checkSortedYearsTest() {
        step("Тестируем сортировку по годам", () -> {
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
        });
    }

    @Test
    @Feature("CheckEmail")
    @DisplayName("Проверка email пользователей на содержание reqres.in и проверка наличия avatar")
    public void checkAvatarAndIdTest() {
        step("Проверяем email пользователя", () -> {
            List<UserData> users = given()
                    .spec(requestSpec(BASE_URL))
                    .when()
                    .get(API_LIST_USERS)
                    .then().log().all()
                    .spec(responseSpec(200))
                    .extract().body().jsonPath().getList("data", UserData.class);
            users.forEach(x -> Assertions.assertTrue(x.getAvatar().contains(x.getId().toString())));
            Assertions.assertTrue(users.stream().allMatch(x -> x.getEmail().endsWith("@reqres.in")));
        });
    }

    @Test
    @Feature("CheckEmail")
    @DisplayName("Проверка email при помощи groovy")
    public void checkEmailTest() {
        step("Тестируем email (groovy)", () -> {
            given()
                    .spec(requestSpec(BASE_URL))
                    .when()
                    .get(API_USERS)
                    .then().log().all()
                    .spec(responseSpec(200))
                    .body("data.findAll{it.email =~/.*?@reqres.in/}.email.flatten()",
                            hasItem("emma.wong@reqres.in"));
        });
    }

    @Test
    @Feature("CheckTime")
    @DisplayName("Сравнение даты в ответе и текущей даты")
    public void checkServerAndPcDateTest() {
        step("Сравниваем дату в ответе и текущую дату", () -> {
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
            String regexForLocalTime = "(.{17})$";
            String currentLocalTime = Clock.systemUTC().instant().toString().replaceAll(regexForLocalTime, "");
            String responseTime = userTimeResponse.getUpdatedAt().replaceAll(regexForResponseTime, "");
            Assertions.assertEquals(responseTime, currentLocalTime);
        });
    }

}
