package com.imaginecup.morpheus.fairy.service;

import com.imaginecup.morpheus.fairy.dto.response.ApproximateStoryDto;

import java.util.List;

public interface FairyService {

    List<String> getRandomTopics();

    String getManufacturedTopic(String prompt);

    ApproximateStoryDto getPlot();

    String getScenario();

    String getChapterImage();

}
