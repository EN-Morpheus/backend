package com.imaginecup.morpheus.fairy.service;

import java.util.List;

public interface FairyService {

    List<String> getRandomTopics();

    String getManufacturedTopic(String prompt);

    String getPlot();

    String getScenario();

    String getChapterImage();

}
