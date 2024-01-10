package com.imaginecup.morpheus.utils;

import com.imaginecup.morpheus.character.dto.request.CharacterCreationForm;
import com.imaginecup.morpheus.utils.constant.Prompt;
import org.json.JSONArray;
import org.json.JSONObject;

public class Parser {

    public static String parseSaveCharacterPrompt(CharacterCreationForm characterCreationForm) {
        String prompt = String.format(Prompt.SAVE_CHARACTER_PROMPT.getPrompt(),
                characterCreationForm.getStyle(),
                characterCreationForm.getIntroduction(),
                characterCreationForm.getAppearance());

        return prompt;
    }

    public static String parseFairyPrompt(String prompt) {
        return null;
    }

    public static String parseGptResponse(String content) {
        return null;
    }

    public static JSONObject extractDataFromResponse(String response) {
        JSONObject jsonResponse = new JSONObject(response);
        JSONArray data = jsonResponse.getJSONArray("data");

        JSONObject result = new JSONObject();
        result.put("data", data);
        return result;
    }

}
