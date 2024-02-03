package com.imaginecup.morpheus.fairy.service;

import com.imaginecup.morpheus.chapter.dao.ChapterRepository;
import com.imaginecup.morpheus.chapter.domain.Chapter;
import com.imaginecup.morpheus.chapter.dto.request.ChapterImageGeneratorDto;
import com.imaginecup.morpheus.chapter.dto.response.ChapterDto;
import com.imaginecup.morpheus.chapter.dto.response.Chapters;
import com.imaginecup.morpheus.chapter.service.ChapterService;
import com.imaginecup.morpheus.character.dao.CharacterRepository;
import com.imaginecup.morpheus.character.domain.Character;
import com.imaginecup.morpheus.fairy.dao.FairyRepository;
import com.imaginecup.morpheus.fairy.dao.TemporaryFairyRepository;
import com.imaginecup.morpheus.fairy.domain.Fairy;
import com.imaginecup.morpheus.fairy.domain.FairyInfo;
import com.imaginecup.morpheus.fairy.domain.TemporaryFairy;
import com.imaginecup.morpheus.fairy.dto.request.FairySaveFormDto;
import com.imaginecup.morpheus.fairy.dto.request.PlotDto;
import com.imaginecup.morpheus.fairy.dto.request.ScenarioDto;
import com.imaginecup.morpheus.fairy.dto.response.ApproximateStoryDto;
import com.imaginecup.morpheus.member.dao.MemberRepository;
import com.imaginecup.morpheus.member.domain.Member;
import com.imaginecup.morpheus.openai.service.OpenaiService;
import com.imaginecup.morpheus.picture.domain.Picture;
import com.imaginecup.morpheus.s3.service.S3Service;
import com.imaginecup.morpheus.utils.ImageSaver;
import com.imaginecup.morpheus.utils.Parser;
import com.imaginecup.morpheus.utils.SecurityUtils;
import com.imaginecup.morpheus.utils.constant.*;
import com.imaginecup.morpheus.utils.response.dto.Response;
import com.imaginecup.morpheus.utils.response.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FairyServiceImpl implements FairyService {

    private final OpenaiService openaiService;
    private final ChapterService chapterService;
    private final S3Service s3Service;
    private final CharacterRepository characterRepository;
    private final MemberRepository memberRepository;
    private final TemporaryFairyRepository temporaryFairyRepository;
    private final ChapterRepository chapterRepository;
    private final FairyRepository fairyRepository;

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

            return ResponseHandler.create200Response(response, responseData);
        } catch (RestClientException e) {
            return ResponseHandler.create500Error(response, e);
        }
    }

    @Override
    public ResponseEntity getPlot(PlotDto plotDto) {
        Response response = new Response();

        try {
            String plotPrompt = getPlotPrompt(plotDto);
            String openaiResponse = openaiService.connectGpt(plotPrompt);
            JSONObject responseJson = Parser.parseContent(openaiResponse);
            ApproximateStoryDto approximateStory = Parser.convertJsonStory(responseJson);

            response.of("result", "SUCCESS");
            response.of("code", approximateStory);

            return new ResponseEntity(response, HttpStatus.OK);
        } catch (RestClientException e) {
            return ResponseHandler.create500Error(response, e);
        } catch (RuntimeException e) {
            return ResponseHandler.create400Error(response, e);
        }
    }

    @Override
    public ResponseEntity getScenario(ScenarioDto scenarioDto) {
        Response response = new Response();
        JSONObject responseJSON = null;

        Parser.escapeScenarioDto(scenarioDto);
        TemporaryFairy temporaryFairy = saveTemporary(scenarioDto);

        try {
            responseJSON = processScenario(scenarioDto);
            Chapters chapters = chapterService.saveChaptersJsonObject(temporaryFairy.getId(), responseJSON);
            chapterService.saveFirstTemporary(temporaryFairy, chapters.getChapters());

            return ResponseHandler.create200Response(response, chapters);
        } catch (RestClientException e) {
            return ResponseHandler.create500Error(response, e);
        } catch (IllegalArgumentException e) {
            Chapters chapters = chapterService.saveChaptersJsonArray(temporaryFairy.getId(), responseJSON);
            return ResponseHandler.create200Response(response, chapters);
        } catch (RuntimeException e) {
            return ResponseHandler.create500Error(response, e);
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

            return ResponseHandler.create200Response(response, imageDataMap);
        } catch (RestClientException e) {
            return ResponseHandler.create500Error(response, e);
        } catch (RuntimeException e) {
            return ResponseHandler.create400Error(response, e);
        }
    }

    @Override
    public ResponseEntity saveTemporaryFairy(Chapters chaptersDto) {
        Response response = new Response();
        try {
            List<Chapter> chapters = chapterRepository.findByTemporaryFairyId(chaptersDto.getTemporaryFairyId());
            chapterService.updateTemporary(chapters, chaptersDto.getChapters());

            return ResponseHandler.create204Response(response, "임시 저장 완료");
        } catch (RuntimeException e) {
            return ResponseHandler.create400Error(response, e);
        } catch (Exception e) {
            return ResponseHandler.create500Error(response, new RuntimeException("파일을 생성하는 데 실패했습니다."));
        }
    }

    @Override
    public ResponseEntity deleteTemporaryFairy(Long temporaryFairyId) {
        TemporaryFairy temporaryFairy = findTemporaryFairy(temporaryFairyId);
        temporaryFairyRepository.delete(temporaryFairy);

        return ResponseHandler.create204Response(new Response(), "삭제 완료");
    }

    @Override
    public ResponseEntity saveFairy(FairySaveFormDto fairySaveFormDto) {
        Response response = new Response();
        try {
            Fairy fairy = saveFairyEntity(fairySaveFormDto);
            chapterService.saveCompleteFairy(fairySaveFormDto.getChapters(), fairySaveFormDto.getTemporaryFairyId(), fairy);

            return ResponseHandler.create204Response(response, "동화 저장 완료");
        } catch (NullPointerException e) {
            return ResponseHandler.create404Error(response, e);
        } catch (RuntimeException e) {
            return ResponseHandler.create400Error(response, e);
        } catch (Exception e) {
            return ResponseHandler.create500Error(response, new RuntimeException("파일을 생성하는 데 실패했습니다."));
        }
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
                scenarioDto.getLinguisticExpression(), 16
        );

        return scenarioPrompt;
    }

    private String getChapterImagePrompt(ChapterImageGeneratorDto chapterImageGeneratorDto) {
        Character character = findCharacter(chapterImageGeneratorDto.getCharacterId());

        String characterPrompt = character.getPrompt();
        String style = StyleDescription.getDescriptionByName(character.getAnimationStyle());

        String chapterImagePrompt = String.format(Prompt.CHAPTER_IMAGE_GENERATOR.getPrompt(),
                chapterImageGeneratorDto.getChapterBackground(), characterPrompt,
                chapterImageGeneratorDto.getChapterStory(), style,
                chapterImageGeneratorDto.getCharacterPosture(),
                character.getPersonality(), character.getName());

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
            throw new IllegalArgumentException("임시 저장 데이터의 ID가 유효하지 않습니다.");
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

    private JSONObject processScenario(ScenarioDto scenarioDto) {
        String scenarioPrompt = getScenarioPrompt(scenarioDto);
        String openaiResponse = openaiService.connectScenarioGpt(scenarioPrompt);
        return Parser.parseContent(openaiResponse);
    }

    private void deleteTemporaryFairyField(Long temporaryId) {
        List<Chapter> chapters = chapterRepository.findByTemporaryFairyId(temporaryId);

        for (Chapter chapter : chapters) {
            chapter.setFairy(null);
        }
    }

    private Fairy saveFairyEntity(FairySaveFormDto saveFormDto) {
        Optional<Member> member = memberRepository.findByMemberId(SecurityUtils.getCurrentMemberId());
        FairyInfo fairyInfo = FairyInfo.builder()
                .title(saveFormDto.getTitle())
                .plot(saveFormDto.getPlot())
                .member(member.get())
                .build();
        Fairy fairy = Fairy.builder().fairyInfo(fairyInfo).build();
        return fairyRepository.save(fairy);
    }
}
