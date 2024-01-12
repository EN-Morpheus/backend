package com.imaginecup.morpheus.utils.constant;

public enum Endpoint {

    DALLE_API_URL("https://api.openai.com/v1/images/generations"),
    GPT_API_URL("https://api.openai.com/v1/chat/completions");

    private final String url;

    Endpoint(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
