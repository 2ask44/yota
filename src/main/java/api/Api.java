package api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import service.Specification;

import static io.restassured.RestAssured.given;

public class Api {

    public String login(String login, String password) {
        return given()
                .spec(Specification.REQ_SPEC)
                //.contentType(ContentType.JSON)
                .body("{ \"login\":\"" + login + "\",\"password\":\"" + password + "\"}")
                .when()
                .post("/login")
                .then()
                .spec(Specification.RES_SPEC)
                .log().all()
                .extract().path("token");
    }

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

    public Response postCustomer(String phone, String token) {
        return given()
                .spec(Specification.REQ_SPEC)
                .body("{\"name\":\"123\", \"phone\":" + phone + ", \"additionalParameters\":{\"string\": \"string\"} }")
                .header("authToken", token)
                .when()
                .post("/customer/postCustomer")
                .then()
                .log().all()
                .extract().response();
    }

    public Response getCustomerById(String customerId, String token) {
        return given()
                .spec(Specification.REQ_SPEC)
                .param("customerId", customerId)
                .header("authToken", token)
                .when()
                .get("/customer/getCustomerById")
                .then()
                .log().all().extract().response();

    }

    public Response changeCustomerStatus(String customerId, String token) {
        return given()
                .spec(Specification.REQ_SPEC)
                .body("{\n" + " \"status\": \"Yes\"\n" + "}")
                .header("authToken", token)
                .when()
                .post("customer/" + customerId + "/changeCustomerStatus")
                .then()
                .log().all().extract().response();

    }


    public Response findByPhoneNumber(long phone, String token) {
        return given()
                .spec(Specification.REQ_SPECXML)
                .body("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                        "<ns3:Envelope xmlns:ns2=\"soap\" xmlns:ns3=\"http://schemas.xmlsoap.org/soap/envelope\">\n" +
                        "<ns2:Header>\n" +
                        "<authToken>" + token + "</authToken>\n" +
                        "</ns2:Header>\n" +
                        "<ns2:Body>\n" +
                        "<phoneNumber>" + phone + "</phoneNumber>\n" +
                        "</ns2:Body>\n" +
                        "</ns3:Envelope>")
                .header("authToken", token)
                .when()
                .post("/customer/findByPhoneNumber")
                .then()
                .log().all().extract().response();
    }
}
