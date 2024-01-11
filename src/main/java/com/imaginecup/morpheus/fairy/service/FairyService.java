package com.imaginecup.morpheus.fairy.service;

import com.imaginecup.morpheus.chapter.dto.ChapterResponseDto;
import com.imaginecup.morpheus.fairy.dto.request.PlotDto;
import com.imaginecup.morpheus.fairy.dto.request.ScenarioDto;
import com.imaginecup.morpheus.fairy.dto.response.ApproximateStoryDto;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FairyService {

    List<String> getRandomTopics();

    ResponseEntity getManufacturedTopic(String prompt);

    ResponseEntity getPlot(PlotDto plotDto);

    ResponseEntity getScenario(ScenarioDto scenarioDto);

    String getChapterImage();

}
