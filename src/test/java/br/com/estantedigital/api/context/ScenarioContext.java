package br.com.estantedigital.api.context;

import io.restassured.response.Response;

public class ScenarioContext {
    private Response response;

    public void setResponse(Response response) {
        this.response = response;
    }

    public Response response() {
        if (response == null) {
            throw new IllegalStateException("Nenhuma resposta HTTP foi armazenada neste cenario");
        }
        return response;
    }
}