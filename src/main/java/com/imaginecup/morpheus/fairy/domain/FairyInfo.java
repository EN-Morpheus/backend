package com.imaginecup.morpheus.fairy.domain;

import com.imaginecup.morpheus.member.domain.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
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
