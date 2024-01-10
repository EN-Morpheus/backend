package com.imaginecup.morpheus.utils.constant;

public enum Prompt {

    CHARACTER_PROMPT("A character in the style of %s animation.\n" +
            "The protagonist's introduction is %s.\n" +
            "The protagonist's appearance is a %s.\n" +
            "The protagonist's personality is %s.\n" +
            "Do not alter the character's prompt.\n" +
            "Don't include letters in the image.");

    private final String prompt;

    Prompt(String prompt) {
        this.prompt = prompt;
    }

    public String getPrompt() {
        return prompt;
    }

}
