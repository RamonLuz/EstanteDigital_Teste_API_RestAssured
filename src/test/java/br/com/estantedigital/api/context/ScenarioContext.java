package br.com.estantedigital.api.context;

import io.restassured.response.Response;

import java.util.concurrent.ThreadLocalRandom;

public class ScenarioContext {
    private Response response;
    private final String uniqueValue = Long.toString(ThreadLocalRandom.current().nextLong(10_000_000_000L, 100_000_000_000L));
    private final int registrationNumber = ThreadLocalRandom.current().nextInt(100_000, 1_000_000);

    public void setResponse(Response response) {
        this.response = response;
    }

    public boolean hasResponse() {
        return response != null;
    }

    public Response response() {
        if (response == null) {
            throw new IllegalStateException("Nenhuma resposta HTTP foi armazenada neste cenario");
        }
        return response;
    }

    public String replacePlaceholders(String value) {
        String resolved = value
                .replace("${unique}", uniqueValue)
                .replace("${registration}", Integer.toString(registrationNumber));
        if (resolved.contains("${id}")) {
            resolved = resolved.replace("${id}", response().jsonPath().getString("id"));
        }
        return resolved;
    }
}