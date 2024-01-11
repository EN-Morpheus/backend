package com.imaginecup.morpheus.fairy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.imaginecup.morpheus.chapter.dto.ChapterResponseDto;
import com.imaginecup.morpheus.character.dao.CharacterRepository;
import com.imaginecup.morpheus.character.domain.Character;
import com.imaginecup.morpheus.fairy.dto.request.PlotDto;
import com.imaginecup.morpheus.fairy.dto.response.ApproximateStoryDto;
import com.imaginecup.morpheus.openai.service.OpenaiService;
import com.imaginecup.morpheus.utils.Parser;
import com.imaginecup.morpheus.utils.constant.Prompt;
import com.imaginecup.morpheus.utils.constant.RandomTopic;
import com.imaginecup.morpheus.utils.dto.DetailResponse;
import com.imaginecup.morpheus.utils.dto.Response;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FairyServiceImpl implements FairyService {

    private final OpenaiService openaiService;
    private final CharacterRepository characterRepository;

    @Override
    public List<String> getRandomTopics() {
        return RandomTopic.getTopicsAsList();
    }

    @Override
    public ResponseEntity getManufacturedTopic(String prompt) {
        Response response = new Response();
        String topicPrompt = String.format(Prompt.USER_TOPIC.getPrompt(), prompt);
        Map<String, Object> responseData = new HashMap<>();

        try {
            String openaiResponse = openaiService.connectGpt(topicPrompt);
            JSONObject responseJson = Parser.parseContent(openaiResponse);
            responseData.put("topic", responseJson.get("topic"));

            response.of("result", "SUCCESS");
            response.of("code", responseData);

            return new ResponseEntity(response, HttpStatus.OK);
        } catch (RestClientException e){
            response.of("result", "FAIL");
            response.of("error", DetailResponse.builder().code(500).message(e.getMessage()).build());

            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity getPlot(PlotDto plotDto) {
        Response response = new Response();

        try{
            String plotPrompt = getPlotPrompt(plotDto);
            String openaiResponse = openaiService.connectGpt(plotPrompt);
            JSONObject responseJson = Parser.parseContent(openaiResponse);
            ApproximateStoryDto approximateStory = new ApproximateStoryDto(responseJson);

            response.of("result", "SUCCESS");
            response.of("code", approximateStory);

            return new ResponseEntity(response, HttpStatus.OK);
        } catch (RestClientException e){
            response.of("result", "FAIL");
            response.of("error", DetailResponse.builder().code(500).message(e.getMessage()).build());

            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (RuntimeException e) {
            response.of("result", "FAIL");
            response.of("error", DetailResponse.builder().code(404).message(e.getMessage()).build());

            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity getScenario() {
        return null;
    }

    @Override
    public String getChapterImage() {
        return null;
    }

    private String getPlotPrompt(PlotDto plotDto) {
        Optional<Character> character = characterRepository.findById(plotDto.getCharacterId());

        if(character.isEmpty()) {
            throw new RuntimeException("캐릭터 ID가 유효하지 않습니다.");
        }
        String plotPrompt = String.format(Prompt.USER_PLOT.getPrompt(),
                plotDto.getTopic(),
                character.get().getName(), character.get().getIntroduction(), character.get().getPersonality());

        return plotPrompt;
    }

}
