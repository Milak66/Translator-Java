package com.server.server;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class Translator {

    private final RestClient restClient;

    public Translator(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://api.mymemory.translated.net")
                .build();
    }

    public String translate(String text, String targetLanguage) {
        MyMemoryResponse response = restClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/get")
                        .queryParam("q", text)
                        .queryParam("langpair", "en|" + targetLanguage)
                        .build())
                .retrieve()
                .body(MyMemoryResponse.class);

        if (response == null
                || response.responseData() == null
                || response.responseData().translatedText() == null) {
            throw new RuntimeException("Translation failed");
        }

        return response.responseData().translatedText();
    }

    private record MyMemoryResponse(
            ResponseData responseData
    ) {}

    private record ResponseData(
            String translatedText
    ) {}
}