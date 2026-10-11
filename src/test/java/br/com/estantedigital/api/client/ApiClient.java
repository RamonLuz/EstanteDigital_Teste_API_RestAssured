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

    public Response request(String method, String path, String body) {
        RequestSpecification request = given().spec(requestSpecification);
        if (body != null) {
            request.body(body);
        }

        return switch (method.toUpperCase()) {
            case "GET" -> request.when().get(path);
            case "POST" -> request.when().post(path);
            case "PUT" -> request.when().put(path);
            case "DELETE" -> request.when().delete(path);
            default -> throw new IllegalArgumentException("Metodo HTTP nao suportado: " + method);
        };
    }
}