package com.server.server;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class Translator {

    private final RestClient restClient;

    public Translator(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://libretranslate.com")
                .build();
    }

    public String translate(String text, String targetLanguage) {
        LibreTranslateResponse response = restClient
                .post()
                .uri("/translate")
                .body(new LibreTranslateRequest(
                        text,
                        "auto",
                        targetLanguage
                ))
                .retrieve()
                .body(LibreTranslateResponse.class);

        if (response == null || response.translatedText() == null) {
            throw new RuntimeException("Translation failed");
        }

        return response.translatedText();
    }

    private record LibreTranslateRequest(
            String q,
            String source,
            String target
    ) {}

    private record LibreTranslateResponse(
            String translatedText
    ) {}
}