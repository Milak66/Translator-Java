package com.server.server;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Server {

    private final Translator translator;

    public Server(Translator translator) {
        this.translator = translator;
    }

    @PostMapping("/translate")
    public Response translate(@RequestBody Request request) {
        String translatedText = translator.translate(
                request.text(),
                request.targetLanguage()
        );

        return new Response(translatedText);
    }
}
