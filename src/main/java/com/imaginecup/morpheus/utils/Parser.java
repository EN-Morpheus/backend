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

    public static List<ChapterDto> convertJsonToDtoList(JSONObject jsonObject) {
        List<ChapterDto> chapterDtoList = new ArrayList<>();

        // Assuming your JSON keys are chapter1, chapter2, chapter3, etc.
        for (int i = 1; i <= jsonObject.length(); i++) {
            JSONObject chapter = jsonObject.getJSONObject("chapter" + i);

            ChapterDto chapterDto = ChapterDto.builder()
                    .story(chapter.getString("story"))
                    .plot(chapter.getString("plot"))
                    .background(chapter.getString("background"))
                    .narrativeText(chapter.getString("narrativeText"))
                    .order(i)
                    .imageUrl(chapter.optString("imageUrl", null))
                    .build();

            chapterDtoList.add(chapterDto);
        }

        return chapterList;
    }

}
