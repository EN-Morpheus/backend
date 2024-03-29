package com.imaginecup.morpheus.fairy.service;

import com.imaginecup.morpheus.chapter.domain.Chapter;
import com.imaginecup.morpheus.chapter.dto.request.ChapterImageGeneratorDto;
import com.imaginecup.morpheus.chapter.dto.response.ChapterDto;
import com.imaginecup.morpheus.chapter.dto.response.Chapters;
import com.imaginecup.morpheus.fairy.dto.request.FairySaveFormDto;
import com.imaginecup.morpheus.fairy.dto.request.PlotDto;
import com.imaginecup.morpheus.fairy.dto.request.ScenarioDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FairyService {

    List<String> getRandomTopics();

    ResponseEntity getManufacturedTopic(String prompt);

    ResponseEntity getPlot(PlotDto plotDto);

    ResponseEntity getScenario(ScenarioDto scenarioDto);

    ResponseEntity getChapterImage(ChapterImageGeneratorDto chapterImageGeneratorDto);

    ResponseEntity saveTemporaryFairy(Chapters chapters);

    ResponseEntity deleteTemporaryFairy(Long temporaryId);

    ResponseEntity saveFairy(FairySaveFormDto fairySaveFormDto);

    ResponseEntity lookupMyFairy();

    List<ChapterDto> chooseFairy(Long fairyId);

}
