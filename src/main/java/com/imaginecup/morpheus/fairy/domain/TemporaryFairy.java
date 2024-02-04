package com.imaginecup.morpheus.fairy.domain;

import com.imaginecup.morpheus.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

@Table(name="temporary_fairys")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class TemporaryFairy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Embedded
    private FairyInfo fairyInfo;

}
