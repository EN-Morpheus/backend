package com.imaginecup.morpheus.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imaginecup.morpheus.chapter.dto.response.ChapterDto;
import com.imaginecup.morpheus.character.dto.request.CharacterCreationForm;
import com.imaginecup.morpheus.fairy.dto.response.ApproximateStoryDto;
import com.imaginecup.morpheus.utils.constant.Prompt;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

    public static List<Object> extractDataFromResponse(String response) {
        JSONObject jsonResponse = new JSONObject(response);
        JSONArray data = jsonResponse.getJSONArray("data");

        JSONObject result = new JSONObject();
        result.put("data", data);

        JSONArray dataArray = result.getJSONArray("data");

        return dataArray.toList();
    }

    public static List<ChapterDto> convertJsonToDtoList(JSONObject jsonObj) {
        List<ChapterDto> chapterList = new ArrayList<>();

        try {
            JSONArray chaptersArray = jsonObj.getJSONArray("chapters");

            for (int i = 0; i < chaptersArray.length(); i++) {
                JSONObject chapterObj = chaptersArray.getJSONObject(i);
                String chapterKey = JSONObject.getNames(chapterObj)[0]; // Get the chapter key (e.g., "chapter1")
                JSONObject chapterDetails = chapterObj.getJSONObject(chapterKey);

                ChapterDto chapter = ChapterDto.builder()
                        .story(chapterDetails.optString("story").replace("\n", " "))
                        .plot(chapterDetails.optString("plot").replace("\n", " "))
                        .background(chapterDetails.optString("background").replace("\n", " "))
                        .narrativeText(chapterDetails.optString("narrativeText").replace("\n", " "))
                        .order(i + 1) // Assuming chapters are in order
                        .build();

                chapterList.add(chapter);
            }
        } catch (Exception e) {
            throw new RuntimeException("openai에서 문제가 발생했습니다.");
        }

        return chapterList;
    }

}
