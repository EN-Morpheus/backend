package com.imaginecup.morpheus.fairy.domain;

import com.imaginecup.morpheus.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FairyInfo {

    @Column(nullable = false)
    private String plot;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

}
