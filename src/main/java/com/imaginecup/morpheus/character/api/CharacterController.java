package com.imaginecup.morpheus.character.api;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.imaginecup.morpheus.character.dto.request.CharacterCreationForm;
import com.imaginecup.morpheus.character.dto.request.ChoosenCharacter;
import com.imaginecup.morpheus.character.dto.request.CreadtedCharacter;
import com.imaginecup.morpheus.character.service.CharacterService;
import com.imaginecup.morpheus.utils.dto.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    public ResponseEntity createImage(@RequestBody CharacterCreationForm characterCreationForm) {
        return null;
    }

    @Operation(summary = "캐릭터 생성")
    @PostMapping(value = "/add",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity add(@Valid @RequestParam("seed") Long seed,
                              @Valid @RequestParam("prompt") String prompt,
                              @Valid @RequestParam("name") String name,
                              @Valid @RequestParam("image") MultipartFile image) {
        CreadtedCharacter character = CreadtedCharacter.builder()
                .seed(seed)
                .prompt(prompt)
                .name(name)
                .image(image)
                .build();

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
