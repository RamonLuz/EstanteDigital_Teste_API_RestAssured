package br.com.estantedigital.api.payload;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class PayloadLoader {
    private static final String PAYLOAD_DIRECTORY = "payloads/";

    public String load(String fileName) {
        String resourceName = PAYLOAD_DIRECTORY + fileName;
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(resourceName)) {
            if (input == null) {
                throw new IllegalArgumentException("Payload nao encontrado: " + resourceName);
            }
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new IllegalStateException("Nao foi possivel ler o payload: " + resourceName, exception);
        }
    }
}