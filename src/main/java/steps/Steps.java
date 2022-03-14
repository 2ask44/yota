package steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import service.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    @Step("Создание нового Кастомера ")
    public String postCustomer(String token) {
        return given()
                .spec(Specification.REQ_SPEC)
                .body("{\"name\":\"123\", \"phone\":79282184620, \"additionalParameters\":{\"string\": \"string\"} }")
                .header("authToken", token)
                .when()
                .post("/customer/postCustomer")
                .then()
                .log().all()
                .extract().path("id");
    }

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

    @Step
    public void retry() throws JsonProcessingException {
        Response response = getEmptyPhone(token);
        String jsonAsString;
        jsonAsString = response.asString();
        int statusCode = response.statusCode();
        //ArrayList<String> jsonAsCollection = new ArrayList(Arrays.asList(response.body()));
        ArrayList<String> jsonAsCollection = new ArrayList<>();
        System.out.print(jsonAsCollection);

        String json = "[{\"id\":1,\"name\":\"Иван\"},{\"id\":2,\"name\":\"Фёдор\"}]";
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> list = mapper.readValue(json, List.class);

        System.out.println(list); // [{id=1, name=Иван}, {id=2, name=Фёдор}]


        await("Подождите пока массив будет не пустой").atMost(300, TimeUnit.MILLISECONDS)
                .until(() -> {
                    if (statusCode != 200) {
                        //isResult = false;
                        retry();
                        return true;
                    }
                    Integer phonesCount = response.jsonPath().getList("phones").size();
                    if (phonesCount == 0) {
                        //isResult.set(false);
                        retry();
                        return true;
                    }
                    return true;
                });
        System.out.print("Тест закончен");

    }
}