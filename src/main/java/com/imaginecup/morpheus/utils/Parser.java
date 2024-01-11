package com.imaginecup.morpheus.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imaginecup.morpheus.character.dto.request.CharacterCreationForm;
import com.imaginecup.morpheus.fairy.dto.response.ApproximateStoryDto;
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

    public static JSONObject parseContent(String response) {
        JSONObject rootObject = new JSONObject(response);

        String contentString = rootObject.getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content");

        JSONObject contentJson = new JSONObject(contentString);

        return contentJson;
    }

    public static ApproximateStoryDto parseJson(String content) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(content, ApproximateStoryDto.class);
    }

    public static JSONObject extractDataFromResponse(String response) {
        JSONObject jsonResponse = new JSONObject(response);
        JSONArray data = jsonResponse.getJSONArray("data");

        JSONObject result = new JSONObject();
        result.put("data", data);
        return result;
    }

}
