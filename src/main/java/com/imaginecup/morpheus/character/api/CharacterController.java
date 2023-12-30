package com.imaginecup.morpheus.character.api;

import com.imaginecup.morpheus.character.dto.request.CharacterInfoDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/character")
@Tag(name = "Character API", description = "캐릭터 추가, 수정, 삭제")
public class CharacterController {

}
