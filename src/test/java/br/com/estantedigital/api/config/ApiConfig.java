package br.com.estantedigital.api.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class ApiConfig {
    private final Properties defaults = loadDefaults();

    public String baseUrl() {
        return setting("api.base-url", "API_BASE_URL")
                .orElseThrow(() -> new IllegalStateException("Configure api.base-url ou API_BASE_URL"));
    }

    public Optional<String> token() {
        return setting("api.token", "API_TOKEN");
    }

    private Optional<String> setting(String propertyName, String environmentName) {
        String environmentValue = System.getenv(environmentName);
        if (environmentValue != null && !environmentValue.isBlank()) {
            return Optional.of(environmentValue);
        }

        String systemValue = System.getProperty(propertyName);
        if (systemValue != null && !systemValue.isBlank()) {
            return Optional.of(systemValue);
        }

        String defaultValue = defaults.getProperty(propertyName);
        return Optional.ofNullable(defaultValue).filter(value -> !value.isBlank());
    }

    private Properties loadDefaults() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config/api.properties")) {
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Nao foi possivel carregar config/api.properties", exception);
        }
        return properties;
    }
}