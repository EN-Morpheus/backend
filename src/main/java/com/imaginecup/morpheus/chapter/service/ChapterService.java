package com.imaginecup.morpheus.chapter.service;

import com.imaginecup.morpheus.chapter.domain.Chapter;
import com.imaginecup.morpheus.chapter.dto.response.ChapterDto;
import com.imaginecup.morpheus.chapter.dto.response.Chapters;
import com.imaginecup.morpheus.fairy.domain.TemporaryFairy;
import org.json.JSONObject;

import java.util.List;

public interface ChapterService {

    Chapters saveChaptersJsonObject(Long temporaryFairyId, JSONObject json);

    Chapters saveChaptersJsonArray(Long temporaryFairyId, JSONObject json);

    void saveFirstTemporary(TemporaryFairy temporaryFairy, List<ChapterDto> chapters);

    List<Chapter> updateTemporary(List<Chapter> chapters, List<ChapterDto> chapterDtos) throws Exception;

    void deleteChapter(Long temporaryId) throws NoSuchFieldException;

}
