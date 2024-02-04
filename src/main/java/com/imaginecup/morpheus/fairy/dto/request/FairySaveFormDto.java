package com.imaginecup.morpheus.fairy.dto.request;

import com.imaginecup.morpheus.chapter.dto.response.ChapterDto;
import lombok.Getter;

import java.util.List;

@Getter
public class FairySaveFormDto {

    private boolean isPublic;
    private String plot;
    private String title;
    private List<ChapterDto> chapters;
    private Long temporaryFairyId;

}
