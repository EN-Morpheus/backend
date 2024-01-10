package com.imaginecup.morpheus.utils;

import com.imaginecup.morpheus.character.dto.request.CharacterCreationForm;
import com.imaginecup.morpheus.utils.constant.Prompt;

public class Parser {

    public static String parseCharacterPrompt(CharacterCreationForm characterCreationForm) {
        String prompt = String.format(Prompt.CHARACTER_PROMPT.getPrompt(),
                characterCreationForm.getStyle(),
                characterCreationForm.getIntroduction(),
                characterCreationForm.getAppearance(),
                characterCreationForm.getPersonality());

        return prompt;
    }

    public static String parseFairyPrompt(String prompt) {
        return null;
    }

    public static String parseGptResponse(String content) {
        return null;
    }

}
