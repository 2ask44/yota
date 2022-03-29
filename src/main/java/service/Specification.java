package service;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Specification {

    public static RequestSpecification REQ_SPEC = new RequestSpecBuilder()
            .setBaseUri("http://10.254.7.187:8090")
            .setContentType(ContentType.JSON)
            .addFilter(new AllureRestAssured())
            .build();

    public static ResponseSpecification RES_SPEC = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();
}

