package br.com.estantedigital.api.steps;

import br.com.estantedigital.api.client.ApiClient;
import br.com.estantedigital.api.config.ApiConfig;
import br.com.estantedigital.api.context.ScenarioContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

public class ApiSteps {
    private final ApiClient apiClient;
    private final ScenarioContext scenarioContext;

    public ApiSteps(ApiConfig config, ScenarioContext scenarioContext) {
        this.apiClient = new ApiClient(config);
        this.scenarioContext = scenarioContext;
    }

    @When("envio uma requisicao GET para {string}")
    public void sendGetRequest(String path) {
        scenarioContext.setResponse(apiClient.get(path));
    }

    @Then("o status da resposta deve ser {int}")
    public void verifyResponseStatus(int expectedStatus) {
        assertThat(scenarioContext.response().statusCode()).isEqualTo(expectedStatus);
    }
}