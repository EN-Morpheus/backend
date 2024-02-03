package com.imaginecup.morpheus.fairy.domain;

import com.imaginecup.morpheus.chapter.domain.Chapter;
import com.imaginecup.morpheus.chapter.dto.response.Chapters;
import com.imaginecup.morpheus.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Embeddable
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FairyInfo {

    @Column(nullable = false)
    @Lob
    private String plot;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

}
