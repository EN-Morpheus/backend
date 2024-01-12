package com.imaginecup.morpheus.fairy.service;

import com.imaginecup.morpheus.chapter.dto.request.ChapterImageGeneratorDto;
import com.imaginecup.morpheus.chapter.dto.response.ChapterDto;
import com.imaginecup.morpheus.character.dao.CharacterRepository;
import com.imaginecup.morpheus.character.domain.Character;
import com.imaginecup.morpheus.fairy.dto.request.PlotDto;
import com.imaginecup.morpheus.fairy.dto.request.ScenarioDto;
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
        } catch (RestClientException e) {
            response.of("result", "FAIL");
            response.of("error", DetailResponse.builder().code(500).message(e.getMessage()).build());

            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity getPlot(PlotDto plotDto) {
        Response response = new Response();

        try {
            String plotPrompt = getPlotPrompt(plotDto);
            String openaiResponse = openaiService.connectGpt(plotPrompt);
            JSONObject responseJson = Parser.parseContent(openaiResponse);
            ApproximateStoryDto approximateStory = new ApproximateStoryDto(responseJson);

            response.of("result", "SUCCESS");
            response.of("code", approximateStory);

            return new ResponseEntity(response, HttpStatus.OK);
        } catch (RestClientException e) {
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
    public ResponseEntity getScenario(ScenarioDto scenarioDto) {
        Response response = new Response();

        try {
            String scenarioPrompt = getScenarioPrompt(scenarioDto);
            String openaiResponse = openaiService.connectGpt(scenarioPrompt);

            JSONObject responseJSON = Parser.parseContent(openaiResponse);
            List<ChapterDto> chapters = Parser.convertJsonToDtoList(responseJSON);

            response.of("result", "SUCCESS");
            response.of("code", chapters);

            return new ResponseEntity(response, HttpStatus.OK);
        } catch (RestClientException e) {
            response.of("result", "FAIL");
            response.of("error", DetailResponse.builder().code(500).message(e.getMessage()).build());

            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (RuntimeException e) {
            response.of("result", "FAIL");
            response.of("error", DetailResponse.builder().code(500).message(e.getMessage()).build());

            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity getChapterImage(ChapterImageGeneratorDto chapterImageGeneratorDto) {
        Response response = new Response();

        try{

        } catch (RuntimeException e) {
            response.of("result", "FAIL");
            response.of("error", e.getMessage());

            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    private String getPlotPrompt(PlotDto plotDto) {
        Character character = findCharacter(plotDto.getCharacterId());

        String plotPrompt = String.format(Prompt.USER_PLOT.getPrompt(),
                plotDto.getTopic(),
                character.getName(), character.getIntroduction(), character.getPersonality());

        return plotPrompt;
    }

    private String getScenarioPrompt(ScenarioDto scenarioDto) {
        String scenarioPrompt = String.format(Prompt.USER_SCENARIO.getPrompt(),
                scenarioDto.getTitle(), scenarioDto.getStory(), scenarioDto.getSubjectMatter(),
                scenarioDto.getPlot(), scenarioDto.getCharacters(),
                scenarioDto.getLinguisticExpression(), scenarioDto.getChapterSize()
        );

        return scenarioPrompt;
    }

    private String getChapterImagePrompt(ChapterImageGeneratorDto chapterImageGeneratorDto) {
        Character character = findCharacter(chapterImageGeneratorDto.getCharacterId());

        String characterPrompt = String.format(Prompt.SAVE_CHARACTER_PROMPT.getPrompt(),
                character.getStyle(), character.getIntroduction(), character.getAppearance());
    }

    private Character findCharacter(Long characterId) {
        Optional<Character> character = characterRepository.findById(characterId);

        if (character.isEmpty()) {
            throw new RuntimeException("캐릭터 ID가 유효하지 않습니다.");
        }

        return character.get();
    }

}
