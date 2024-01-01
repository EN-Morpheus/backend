package com.imaginecup.morpheus.character.api;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.imaginecup.morpheus.character.dto.request.CharacterInfoDto;
import com.imaginecup.morpheus.character.service.CharacterService;
import com.imaginecup.morpheus.utils.dto.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/character")
@Tag(name = "Character API", description = "캐릭터 조회, 추가, 수정, 삭제, 창 늘리기")
public class CharacterController {

    private final CharacterService characterService;

    @PostMapping(value = "/add", consumes = "multipart/form-data")
    public ResponseEntity add(@RequestPart(value = "seed") Long seed,
                              @RequestPart(value = "prompt") String prompt,
                              @RequestPart(value = "name") String name,
                              @RequestPart(value = "image") MultipartFile image) {
        CharacterInfoDto character = CharacterInfoDto.builder()
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
