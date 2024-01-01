package com.imaginecup.morpheus.character.api;

import com.imaginecup.morpheus.character.dto.request.CharacterInfoDto;
import com.imaginecup.morpheus.character.service.CharacterService;
import com.imaginecup.morpheus.utils.dto.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/character")
@Tag(name = "Character API", description = "캐릭터 추가, 수정, 삭제, 창 늘리기")
public class CharacterController {

    private final CharacterService characterService;

    @Operation(summary = "캐릭터 생성")
    @PostMapping("/add")
    public void addCharacter(@RequestBody CharacterInfoDto characterInfo) {

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
