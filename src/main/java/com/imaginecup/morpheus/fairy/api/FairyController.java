package com.imaginecup.morpheus.fairy.api;

import com.imaginecup.morpheus.chapter.dto.ChapterResponseDto;
import com.imaginecup.morpheus.fairy.dto.request.PlotDto;
import com.imaginecup.morpheus.fairy.dto.request.ScenarioDto;
import com.imaginecup.morpheus.fairy.dto.response.ApproximateStoryDto;
import com.imaginecup.morpheus.fairy.service.FairyService;
import com.imaginecup.morpheus.utils.dto.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fairy")
@Tag(name = "Fairy API", description = "동화 제작")
public class FairyController {

    private final FairyService fairyService;

    @Operation(summary = "랜덤 주제 조회")
    @GetMapping("/topic-random")
    public ResponseEntity lookupRandomTopics() {
        List<String> topics = fairyService.getRandomTopics();

        Response response = new Response();
        response.of("result", "SUCCESS");
        response.of("code", topics);

        return new ResponseEntity(response, HttpStatus.OK);
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

}
