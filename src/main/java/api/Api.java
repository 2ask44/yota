package api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import service.Specification;

import static io.restassured.RestAssured.given;

public class Api {

    private String token;

    public void login() {
        token = given()
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

    public Response getEmptyPhone() {
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

    public String postCustomer(String phone) {
        Response response = given()
                .spec(Specification.REQ_SPEC)
                .body("{\"name\":\"123\", \"phone\":" + phone + ", \"additionalParameters\":{\"string\": \"string\"} }")
                .header("authToken", token)
                .when()
                .post("/customer/postCustomer")
                .then()
                .log().all()
                .extract().response();
        if (response.statusCode() == 200) {
            return response.path("id");
        } else {
            //System.out.print("Номер занят " + phone);
            return "";
        }
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


    public void getCustomerById() {
        given()
                .spec(Specification.REQ_SPEC)
                .param("customerId", postCustomer(token))
                .header("authToken", token)
                .when()
                .get("/customer/getCustomerById")
                .then()
                .log().all();
    }
/*
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
}
