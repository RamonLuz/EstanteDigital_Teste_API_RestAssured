package br.com.estantedigital.api.client;

import br.com.estantedigital.api.config.ApiConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ApiClient {
    private final RequestSpecification requestSpecification;

    public ApiClient(ApiConfig config) {
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(config.baseUrl())
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON);
        config.token().ifPresent(token -> builder.addHeader("Authorization", "Bearer " + token));
        this.requestSpecification = builder.build();
    }

    public Response get(String path) {
        return given().spec(requestSpecification).when().get(path);
    }

    public Response post(String path, Object body) {
        return given().spec(requestSpecification).body(body).when().post(path);
    }

    public Response put(String path, Object body) {
        return given().spec(requestSpecification).body(body).when().put(path);
    }

    public Response delete(String path) {
        return given().spec(requestSpecification).when().delete(path);
    }
}