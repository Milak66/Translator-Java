package com.server.server;

public record Request(
        String text,
        String targetLanguage
) {}
