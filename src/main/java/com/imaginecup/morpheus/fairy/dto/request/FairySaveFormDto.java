package com.imaginecup.morpheus.fairy.dto.request;

import com.imaginecup.morpheus.chapter.dto.response.ChapterDto;
import com.imaginecup.morpheus.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

import java.util.List;

@Getter
public class FairySaveFormDto {

    private boolean isPublic;
    private String plot;
    private String title;
    private Member member;
    private List<ChapterDto> chapters;
    private Long temporaryFairyId;

}
