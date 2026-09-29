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
        try {
            MyMemoryResponse response = restClient
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/get")
                            .queryParam("q", text)
                            .queryParam("langpair", "en|" + targetLanguage)
                            .build())
                    .retrieve()
                    .body(MyMemoryResponse.class);

            if (response == null || response.responseData() == null) {
                throw new RuntimeException("MyMemory returned an empty response");
            }

            return response.responseData().translatedText();

        } catch (Exception e) {
            System.err.println("Translation error:");
            e.printStackTrace();

            throw new RuntimeException("Translation API failed: " + e.getMessage(), e);
        }
    }

    private record MyMemoryResponse(
            ResponseData responseData
    ) {}

    private record ResponseData(
            String translatedText
    ) {}
}