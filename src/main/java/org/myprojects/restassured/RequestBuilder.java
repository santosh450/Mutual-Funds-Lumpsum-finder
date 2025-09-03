package org.myprojects.restassured;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public final class RequestBuilder {

    private RequestBuilder(){}

    public static Response getRequest(int id){
        return given()
                .log()
                .all()
                .baseUri("https://api.mfapi.in")
                .basePath("/mf")
                .pathParam("id", id)
                .get("/{id}");
    }
}
