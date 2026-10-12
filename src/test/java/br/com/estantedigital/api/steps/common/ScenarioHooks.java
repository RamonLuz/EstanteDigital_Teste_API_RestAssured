package br.com.estantedigital.api.steps.common;

import br.com.estantedigital.api.context.ScenarioContext;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.restassured.response.Response;

public class ScenarioHooks {
    private final ScenarioContext scenarioContext;

    public ScenarioHooks(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed() && scenarioContext.hasResponse()) {
            Response response = scenarioContext.response();
            String details = "HTTP status: " + response.statusCode() + System.lineSeparator() + response.asString();
            scenario.attach(details, "text/plain", "Resposta HTTP");
        }
    }
}