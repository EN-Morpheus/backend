package com.imaginecup.morpheus.fairy.service;

import com.imaginecup.morpheus.chapter.dto.request.ChapterImageGeneratorDto;
import com.imaginecup.morpheus.chapter.dto.response.ChapterDto;
import com.imaginecup.morpheus.chapter.dto.response.Chapters;
import com.imaginecup.morpheus.character.dao.CharacterRepository;
import com.imaginecup.morpheus.character.domain.Character;
import com.imaginecup.morpheus.fairy.dao.TemporaryFairyRepository;
import com.imaginecup.morpheus.fairy.domain.FairyInfo;
import com.imaginecup.morpheus.fairy.domain.TemporaryFairy;
import com.imaginecup.morpheus.fairy.dto.request.PlotDto;
import com.imaginecup.morpheus.fairy.dto.request.ScenarioDto;
import com.imaginecup.morpheus.fairy.dto.response.ApproximateStoryDto;
import com.imaginecup.morpheus.member.dao.MemberRepository;
import com.imaginecup.morpheus.member.domain.Member;
import com.imaginecup.morpheus.openai.service.OpenaiService;
import com.imaginecup.morpheus.utils.Parser;
import com.imaginecup.morpheus.utils.SecurityUtils;
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
    private final MemberRepository memberRepository;
    private final TemporaryFairyRepository temporaryFairyRepository;

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
        JSONObject responseJSON = null;

        TemporaryFairy temporaryFairy = saveTemporary(scenarioDto);

        try {
            String scenarioPrompt = getScenarioPrompt(scenarioDto);
            String openaiResponse = openaiService.connectGpt(scenarioPrompt);

            responseJSON = Parser.parseContent(openaiResponse);

            Chapters chapters = Chapters.builder()
                    .temporaryFairyId(temporaryFairy.getId())
                    .chapters(Parser.convertJsonObject(responseJSON))
                    .build();

            response.of("result", "SUCCESS");
            response.of("code", chapters);

            return new ResponseEntity(response, HttpStatus.OK);
        } catch (RestClientException e) {
            response.of("result", "FAIL");
            response.of("error", DetailResponse.builder().code(500).message(e.getMessage()).build());

            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException e) {
            Chapters chapters = Chapters.builder()
                    .chapters(Parser.convertJsonArray(responseJSON))
                    .temporaryFairyId(temporaryFairy.getId())
                    .build();

            response.of("result", "SUCCESS");
            response.of("code", chapters);

            return new ResponseEntity(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            response.of("result", "FAIL");
            response.of("error", DetailResponse.builder().code(500).message(e.getMessage()).build());

            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity getChapterImage(ChapterImageGeneratorDto chapterImageGeneratorDto) {
        Response response = new Response();

        try {
            String imagePrompt = getChapterImagePrompt(chapterImageGeneratorDto);
            String openaiResponse = openaiService.generatePicture(imagePrompt);

            List<Object> imageData = Parser.extractDataFromResponse(openaiResponse);

            Map<String, Object> imageDataMap = new HashMap<>();
            imageDataMap.put("data", imageData);

            response.of("result", "SUCCESS");
            response.of("image url", imageDataMap);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RestClientException e) {
            response.of("result", "FAIL");
            response.of("error", DetailResponse.builder().code(500).message(e.getMessage()).build());

            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (RuntimeException e) {
            response.of("result", "FAIL");
            response.of("error", DetailResponse.builder().code(500).message(e.getMessage()).build());

            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity saveTemporaryFairy(Chapters chapters) {
        TemporaryFairy temporaryFairy = findTemporaryFairy(chapters.getTemporaryFairyId());
        FairyInfo fairyInfo = FairyInfo.builder()
                .
                .build();

        return null;
    }

    @Override
    public ResponseEntity deleteTemporaryFairy(Chapters chapters) {
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

        String chapterImagePrompt = String.format(Prompt.CHAPTER_IMAGE_GENERATOR.getPrompt(),
                chapterImageGeneratorDto.getChapterBackground(), characterPrompt,
                chapterImageGeneratorDto.getChapterStory(), character.getPersonality(),
                character.getName());

        return chapterImagePrompt;
    }

    private Character findCharacter(Long characterId) {
        Optional<Character> character = characterRepository.findById(characterId);

        if (character.isEmpty()) {
            throw new RuntimeException("캐릭터 ID가 유효하지 않습니다.");
        }

        return character.get();
    }

    private Member findMember(String memberId) {
        Optional<Member> member = memberRepository.findByMemberId(memberId);

        return member.get();
    }

    private TemporaryFairy findTemporaryFairy(Long temporaryFairyId) {
        Optional<TemporaryFairy> temporaryFairy = temporaryFairyRepository.findById(temporaryFairyId);

        if (temporaryFairy.isEmpty()) {
            throw new RuntimeException("임시 저장 데이터의 ID가 유효하지 않습니다.");
        }

        return temporaryFairy.get();
    }

    private TemporaryFairy saveTemporary(ScenarioDto scenarioDto) {
        FairyInfo fairyInfo = FairyInfo.builder()
                .member(findMember(SecurityUtils.getCurrentMemberId()))
                .plot(scenarioDto.getPlot())
                .title(scenarioDto.getTitle())
                .build();

        TemporaryFairy temporaryFairy = TemporaryFairy.builder()
                .fairyInfo(fairyInfo)
                .build();
        TemporaryFairy savedFairy = temporaryFairyRepository.save(temporaryFairy);

        return savedFairy;
    }

}
