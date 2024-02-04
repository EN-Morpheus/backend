package com.imaginecup.morpheus.utils.constant;

public enum Role {

    SYSTEM_AUTHOR("From now on, you are a writer who writes children's stories for world-class children."),
    SYSTEM_JSON_SCHEMA("provide your answer in JSON structure like this { \"chapter1\": {\"story\": \"<plot>\", \"plot\": \"<configuration,>\" \"background\": \"<background>\", \"narrativeText\": \"<fairy tale book fingerprint and dialogue>\", \"characterPosture\": \"Specifying the character's most natural posture under the above conditions\"}, \"chapter2\": {\"story\": \"<plot>\", \"plot\": \"<configuration,>\" \"background\": \"<background>\", \"narrativeText\": \"<fairy tale book fingerprint and dialogue>\", \"characterPosture\": \"Specifying the character's most natural posture under the above conditions\"} }");

    private final String prompt;

    Role(String prompt) {
        this.prompt = prompt;
    }

    public String getPrompt() {
        return prompt;
    }

}
