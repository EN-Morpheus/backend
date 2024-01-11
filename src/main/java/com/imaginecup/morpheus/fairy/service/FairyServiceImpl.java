package com.imaginecup.morpheus.fairy.service;

import com.imaginecup.morpheus.chapter.dto.ChapterResponseDto;
import com.imaginecup.morpheus.fairy.dto.request.PlotDto;
import com.imaginecup.morpheus.fairy.dto.response.ApproximateStoryDto;
import com.imaginecup.morpheus.utils.constant.RandomTopic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FairyServiceImpl implements FairyService {

    @Override
    public List<String> getRandomTopics() {
        return RandomTopic.getTopicsAsList();
    }

    @Override
    public String getManufacturedTopic(String prompt) {

        return null;
    }

    @Override
    public ApproximateStoryDto getPlot(PlotDto plotDto) {
        return null;
    }

    @Override
    public List<ChapterResponseDto> getScenario() {
        return null;
    }

    @Override
    public String getChapterImage() {
        return null;
    }

}
