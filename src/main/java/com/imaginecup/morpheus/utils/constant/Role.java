package com.imaginecup.morpheus.utils.constant;

public enum Role {

    SYSTEM_AUTHOR("지금부터 너는 세계적인 어린이들을 위한 동화책을 쓰는 작가야");

    private final String prompt;

    Role(String prompt) {
        this.prompt = prompt;
    }

    public String getPrompt() {
        return prompt;
    }

}
