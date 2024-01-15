package com.imaginecup.morpheus.chapter.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class Chapters {

    private Long temporaryFairyId;

    private List<ChapterDto> chapters;

}
