package com.imaginecup.morpheus.utils.constant;

public enum CharacterPrompt {
    ID("and is positioned like an ID photo, looking straight ahead, with the image framed from his chest up to the top of his head"),
    POSTURE("He is sitting in a lush green forest with sunlight filtering through the trees.\n");

    private String message;

    CharacterPrompt(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
