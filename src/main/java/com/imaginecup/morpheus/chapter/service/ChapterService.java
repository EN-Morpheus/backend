package com.imaginecup.morpheus.chapter.service;

import com.imaginecup.morpheus.chapter.dto.response.Chapters;
import org.json.JSONObject;

public interface ChapterService {

    Chapters saveChaptersJsonObject(Long temporaryFairyId, JSONObject json);

    Chapters saveChaptersJsonArray(Long temporaryFairyId, JSONObject json);

}
