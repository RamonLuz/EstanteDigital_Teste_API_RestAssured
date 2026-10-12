package br.com.estantedigital.api.payload;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class PayloadLoader {
    public String load(String fileName) throws IOException {
        try (InputStream input = getClass().getResourceAsStream("/payloads/" + fileName)) {
            if (input == null) {
                throw new IllegalArgumentException("Payload nao encontrado: " + fileName);
            }
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}