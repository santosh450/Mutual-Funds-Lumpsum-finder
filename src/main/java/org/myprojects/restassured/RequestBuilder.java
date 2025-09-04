package org.myprojects.restassured;

import io.restassured.response.Response;
import org.myprojects.Constants.FrameworkConstants;

import java.io.File;

import static io.restassured.RestAssured.given;

public final class RequestBuilder {

    private RequestBuilder(){}

    public static Response getMFRequest(int id){
        return given()
                .log()
                .all()
                .baseUri("https://api.mfapi.in")
                .basePath("/mf")
                .pathParam("id", id)
                .get("/{id}");
    }

    public static Response sendReportToTelegram(File file){
        return given()
                .log()
                .all()
                .multiPart("chat_id", FrameworkConstants.CHAT_ID)
                .multiPart("photo", file)
                .when()
                .post("https://api.telegram.org/bot" + FrameworkConstants.BOT_TOKEN + "/sendPhoto");
    }


}
