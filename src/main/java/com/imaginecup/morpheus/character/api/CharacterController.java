package com.imaginecup.morpheus.character.api;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.imaginecup.morpheus.character.dto.request.CharacterCreationForm;
import com.imaginecup.morpheus.character.dto.request.SavedCharacter;
import com.imaginecup.morpheus.character.dto.response.CharacterInfo;
import com.imaginecup.morpheus.character.service.CharacterService;
import com.imaginecup.morpheus.utils.Parser;
import com.imaginecup.morpheus.utils.dto.DetailResponse;
import com.imaginecup.morpheus.utils.dto.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/character")
@Tag(name = "Character API", description = "캐릭터 조회, 추가, 삭제, 창 늘리기")
public class CharacterController {

    private final CharacterService characterService;

    @Operation(summary = "캐릭터 조회", description = "마이페이지에서의 캐릭터 조회, 동화 생성 중, 캐릭터 조회할 때 사용")
    @GetMapping("/list")
    public ResponseEntity lookup() {
        return characterService.lookup();
    }

    @Operation(summary = "캐릭터 삭제")
    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestParam("id") Long characterId) {
        Response response = new Response();
        try {
            characterService.deleteCharacter(characterId);

            response.of("result", "SUCCESS");
            response.of("code", DetailResponse.builder().code(202).message("캐릭터가 삭제되었습니다.").build());

            return new ResponseEntity(response, HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            response.of("result", "FAIL");
            response.of("error", DetailResponse.builder().code(404).message(e.getMessage()).build());

            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "캐릭터 선택", description = "동화 생성 중, 동화 주인공을 선택할 때 사용")
    @GetMapping("/pick")
    public ResponseEntity pick(@RequestParam("id") Long characterId) {
        Response response = new Response();
        try {
            CharacterInfo character = characterService.pickCharacter(characterId);

            response.of("result", "SUCCESS");
            response.of("code", character);

            return new ResponseEntity(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            response.of("result", "FAIL");
            response.of("error", DetailResponse.builder().code(404).message(e.getMessage()).build());

            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "캐릭터 제안 이미지 생성")
    @PostMapping("/create")
    public ResponseEntity createImage(@RequestBody CharacterCreationForm characterCreationForm) {
        String prompt = Parser.parseSaveCharacterPrompt(characterCreationForm);
        return characterService.createImage(prompt);
    }

    @Operation(summary = "캐릭터 생성")
    @PostMapping(value = "/add",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity add(@RequestBody SavedCharacter character) {
        try {
            Response response = characterService.addCharacter(character);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (AmazonS3Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        } catch (RuntimeException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "캐릭터 방 추가")
    @PatchMapping("increase-character")
    public ResponseEntity increaseCountOfCharacter() {
        try {
            characterService.increaseCharacterRoom();
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

}
