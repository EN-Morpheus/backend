package com.imaginecup.morpheus.fairy.domain;

import com.imaginecup.morpheus.member.domain.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "fairys")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
public class Fairy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isPublic;

    @Column(nullable = false)
    private boolean isComplete;

    @Embedded
    private FairyInfo fairyInfo;

}
