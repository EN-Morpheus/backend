package com.imaginecup.morpheus.fairy.api;

import com.imaginecup.morpheus.fairy.service.FairyService;
import com.imaginecup.morpheus.utils.dto.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        String manufacturedTopic = fairyService.getManufacturedTopic(topic)
        Response response = new Response();
        response.of("result", "SUCCESS");
        response.of("code", manufacturedTopic);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    public ResponseEntity getStory() {
        Response response = new Response();
        response.of("result", "SUCCESS");

        return new ResponseEntity(response, HttpStatus.OK);
    }

}
