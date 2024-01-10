package com.imaginecup.morpheus.character.api;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.imaginecup.morpheus.character.dto.request.CharacterCreationForm;
import com.imaginecup.morpheus.character.dto.request.SavedCharacter;
import com.imaginecup.morpheus.character.service.CharacterService;
import com.imaginecup.morpheus.utils.Parser;
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
@Tag(name = "Character API", description = "캐릭터 조회, 추가, 수정, 삭제, 창 늘리기")
public class CharacterController {

    private final CharacterService characterService;

    @Operation(summary = "캐릭터 조회")
    @GetMapping("/lookup")
    public ResponseEntity lookup() {
        return characterService.lookup();
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
