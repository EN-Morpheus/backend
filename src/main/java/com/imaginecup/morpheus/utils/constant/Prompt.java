package com.imaginecup.morpheus.utils.constant;

public enum Prompt {

    SAVE_CHARACTER_PROMPT("A character in the style of %s animation.\n" +
            "The protagonist's introduction is %s.\n" +
            "The protagonist's appearance is a %s.\n" +
            "Do not alter the character's prompt.\n" +
            "Don't include letters in the image.\n" +
            "There should be one person in the image");

    private final String prompt;

    Prompt(String prompt) {
        this.prompt = prompt;
    }

    public String getPrompt() {
        return prompt;
    }

}
