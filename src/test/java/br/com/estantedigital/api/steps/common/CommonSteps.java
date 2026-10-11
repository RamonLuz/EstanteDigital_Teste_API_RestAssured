package br.com.estantedigital.api.steps.common;

import br.com.estantedigital.api.client.ApiClient;
import br.com.estantedigital.api.config.ApiConfig;
import br.com.estantedigital.api.context.ScenarioContext;
import br.com.estantedigital.api.payload.PayloadLoader;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CommonSteps {
    private final ApiClient apiClient;
    private final ScenarioContext scenarioContext;
    private final PayloadLoader payloadLoader;

    public CommonSteps(ApiConfig config, ScenarioContext scenarioContext) {
        this.apiClient = new ApiClient(config);
        this.scenarioContext = scenarioContext;
        this.payloadLoader = new PayloadLoader();
    }

    @When("envio uma requisicao {word} para {string}")
    public void sendRequest(String method, String path) {
        scenarioContext.setResponse(apiClient.request(method, scenarioContext.resolve(path), null));
    }

    @When("envio uma requisicao {word} para {string} com o payload {string}")
    public void sendRequestWithPayload(String method, String path, String payloadFile) {
        String body = scenarioContext.resolve(payloadLoader.load(payloadFile));
        scenarioContext.setResponse(apiClient.request(method, scenarioContext.resolve(path), body));
    }

    @Then("o status da resposta deve ser {int}")
    public void verifyResponseStatus(int expectedStatus) {
        assertThat(scenarioContext.response().statusCode()).isEqualTo(expectedStatus);
    }

    @Then("o campo JSON {string} deve ser {string}")
    public void verifyJsonField(String jsonPath, String expectedValue) {
        assertThat(scenarioContext.response().jsonPath().getString(jsonPath))
                .isEqualTo(scenarioContext.resolve(expectedValue));
    }

    @Then("o campo JSON {string} deve ser um numero positivo")
    public void verifyPositiveJsonNumber(String jsonPath) {
        Number value = scenarioContext.response().jsonPath().get(jsonPath);
        assertThat(value).isNotNull();
        assertThat(value.longValue()).isPositive();
    }

    @Then("a lista JSON deve conter {string} no campo {string}")
    public void verifyListContainsValue(String expectedValue, String jsonPath) {
        List<String> values = scenarioContext.response().jsonPath().getList(jsonPath, String.class);
        assertThat(values).contains(scenarioContext.resolve(expectedValue));
    }
}