package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojos.Pojo;
import service.Specification;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.awaitility.Awaitility.await;
import static service.BaseTest.token;


public class Steps {


    public static String jsonAsString;


    @Step("Получение токена по логину и паролю")
    public String login() {
        return given()
                //.baseUri("http://10.254.7.187:8090")
                .spec(Specification.REQ_SPEC)
                //.contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"login\": \"admin\",\n" +
                        "    \"password\": \"password\"\n" +
                        "}")
                .when()
                .post("/login")
                .then()
                //.statusCode(200)
                .spec(Specification.RES_SPEC)
                .log().all()
                .extract().path("token");
    }

    //  @Step("Получение списка свободных номеров  ")
    //public List<Pojo> getEmptyPhone(String token) {
    //Response response = getEmptyPhone();
    //int statusCode = 200;
    // List<Pojo> phone =
    //given()
    //  .spec(Specification.REQ_SPEC)
    // .header("authToken", token)
    //  .when()
    //   .get("/simcards/getEmptyPhone")
    //   .then()
    //  .contentType(ContentType.JSON)
    // .log().all()
    //.extract().jsonPath().getList("phones", Pojo.class);
    //return phone;
    //System.out.print(phone.);
//}

    @Step("Получение списка свободных номеров  ")
    public Response getEmptyPhone(String token) {
        return given()
                .spec(Specification.REQ_SPEC)
                .header("authToken", token)
                .when()
                .get("/simcards/getEmptyPhone")
                .then()
                .contentType(ContentType.JSON)
                .log().all()
                .extract().response();
    }


  /*  @Step("Создание нового Кастомера ")
    public String postCustomer(String token) {
        for (int i = 0; i < phone.size(); i++) {
            Response response = given()
                    .spec(Specification.REQ_SPEC)
                    .body("{\"name\":\"123\", \"phone\":" + phone.get(i) + ", \"additionalParameters\":{\"string\": \"string\"} }")
                    .header("authToken", token)
                    .when()
                    .post("/customer/postCustomer")
                    .then()
                    .log().all()
                    .extract().response();
            if (response.statusCode() == 200) {
                return response.path("id");
            }
        }
        return null;
    }*/

/*
    @Step("Поиск по Кастомера ID")
    public void getCustomerById(String token) {
        given()
                .spec(Specification.REQ_SPEC)
                .param("customerId", postCustomer(token))
                .header("authToken", token)
                .when()
                .get("/customer/getCustomerById")
                .then()
                .log().all();
    }

    @Step("Изменеие Статуса Кастомера  ")
    public void changeCustomerStatus(String token) {
        String customerId = postCustomer(token);
        given()
                .spec(Specification.REQ_SPEC)
                .body("{\n" +
                        "    \"status\": \"Yes\"\n" +
                        "}")
                .header("authToken", token)
                .when()
                .post("customer/" + customerId + "/changeCustomerStatus")
                .then()
                .log().all();

    }
*/

    @Step
    public List<String> retry() {
        AtomicReference<List<String>> listPhone = new AtomicReference<>();
        await("Подождите пока массив будет не пустой").atMost(500, TimeUnit.MILLISECONDS)
                .until(() -> {
                    Response response = getEmptyPhone(token);
                    boolean success = response.statusCode() == 200 && response.jsonPath().getList("phones").size() > 0;
                    if (success) {
                        listPhone.set(response.body().jsonPath().getList("phones", Pojo.class)
                                .stream().map(p -> Long.toString(p.getPhone())).collect(Collectors.toList()));
                    }
                    return success;
                });
        System.out.println(listPhone);
        return listPhone.get();
    }

}