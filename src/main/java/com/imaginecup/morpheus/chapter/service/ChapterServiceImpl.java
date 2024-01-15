package com.imaginecup.morpheus.chapter.service;

import com.imaginecup.morpheus.chapter.dto.response.Chapters;
import com.imaginecup.morpheus.utils.Parser;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChapterServiceImpl implements ChapterService {

    @Override
    public Chapters saveChaptersJsonObject(Long temporaryFairyId, JSONObject json) {
        return Chapters.builder()
                .temporaryFairyId(temporaryFairyId)
                .chapters(Parser.convertJsonObject(json))
                .build();
    }

    @Override
    public Chapters saveChaptersJsonArray(Long temporaryFairyId, JSONObject json) {
        return Chapters.builder()
                .temporaryFairyId(temporaryFairyId)
                .chapters(Parser.convertJsonArray((JSONObject) json))
                .build();
    }
}
