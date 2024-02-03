package com.imaginecup.morpheus.fairy.api;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.imaginecup.morpheus.chapter.dto.request.ChapterImageGeneratorDto;
import com.imaginecup.morpheus.chapter.dto.response.Chapters;
import com.imaginecup.morpheus.chapter.service.ChapterService;
import com.imaginecup.morpheus.fairy.dto.request.FairySaveFormDto;
import com.imaginecup.morpheus.fairy.dto.request.PlotDto;
import com.imaginecup.morpheus.fairy.dto.request.ScenarioDto;
import com.imaginecup.morpheus.fairy.service.FairyService;
import com.imaginecup.morpheus.utils.response.ResponseHandler;
import com.imaginecup.morpheus.utils.response.dto.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fairy")
@Tag(name = "Fairy API", description = "동화 제작")
public class FairyController {

    private final FairyService fairyService;
    private final ChapterService chapterservice;

    @Operation(summary = "랜덤 주제 조회")
    @GetMapping("/topic-random")
    public ResponseEntity lookupRandomTopics() {
        List<String> topics = fairyService.getRandomTopics();

        return ResponseHandler.create200Response(new Response(), topics);
    }

    @Operation(summary = "주제 가공")
    @GetMapping("/topic-manufacture")
    public ResponseEntity ManufactureTopic(@RequestParam("topic") String topic) {
        return fairyService.getManufacturedTopic(topic);
    }

    @Operation(summary = "대략적인 줄거리 제공")
    @PostMapping("/prompt")
    public ResponseEntity getStory(@RequestBody PlotDto plotDto) {
        return fairyService.getPlot(plotDto);
    }

    @Operation(summary = "세부 줄거리")
    @PostMapping("/actualization")
    public ResponseEntity getScenario(@RequestBody ScenarioDto scenarioDto) {
        return fairyService.getScenario(scenarioDto);
    }

    @Operation(summary = "챕터 당 이미지 생성")
    @PostMapping("/chapter-image")
    public ResponseEntity getChapterImage(@RequestBody ChapterImageGeneratorDto chapterImageGeneratorDto) {
        return fairyService.getChapterImage(chapterImageGeneratorDto);
    }

    @Operation(summary = "임시 저장")
    @PostMapping("/temporary/save")
    public ResponseEntity saveTemporary(@RequestBody Chapters chapters) {
        return fairyService.saveTemporaryFairy(chapters);
    }

    @Operation(summary = "임시 저장 데이터 삭제")
    @DeleteMapping("temporary/delete")
    public ResponseEntity deleteTemporary(@RequestParam("temporaryFairyId") Long temporaryFairyId) {
        try {
            chapterservice.deleteChapter(temporaryFairyId);
            return fairyService.deleteTemporaryFairy(temporaryFairyId);
        } catch (IllegalArgumentException | NoSuchFieldException e) {
            return ResponseHandler.create400Error(new Response(), e);
        } catch (AmazonS3Exception e) {
            return ResponseHandler.create500Error(new Response(), e);
        } catch (RuntimeException e) {
            return ResponseHandler.create400Error(new Response(), e);
        }
    }

    @Operation(summary = "동화 저장")
    @PostMapping("save")
    public ResponseEntity saveFairy(@RequestBody FairySaveFormDto fairySaveFormDto) {
        return fairyService.saveFairy(fairySaveFormDto);
    }

}
