package com.imaginecup.morpheus.chapter.service;

import com.imaginecup.morpheus.chapter.domain.Chapter;
import com.imaginecup.morpheus.chapter.dto.response.Chapters;
import com.imaginecup.morpheus.fairy.domain.TemporaryFairy;
import org.json.JSONObject;

public interface ChapterService {

    Chapters saveChaptersJsonObject(Long temporaryFairyId, JSONObject json);

    Chapters saveChaptersJsonArray(Long temporaryFairyId, JSONObject json);

    void saveFirstTemporary(TemporaryFairy temporaryFairy, Chapters chapters) throws Exception;

    Chapter updateTemporary(TemporaryFairy temporaryFairy, Chapters chapters);

}
