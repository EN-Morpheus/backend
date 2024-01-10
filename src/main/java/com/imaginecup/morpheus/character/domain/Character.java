package com.imaginecup.morpheus.character.domain;

import com.imaginecup.morpheus.member.domain.Member;
import com.imaginecup.morpheus.picture.domain.Picture;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "characters")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Character {

    @Id
    @GeneratedValue
    @Column(updatable = false, unique = true, nullable = false)
    private Long id;

    @Lob
    @Column(nullable = false)
    private String prompt;

    @Lob
    @Column(nullable = false)
    private String revisedPrompt;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String personality;

    @Column(nullable = false)
    private String introduction;

    @JoinColumn(nullable = false)
    @OneToOne(cascade = CascadeType.ALL)
    private Picture picture;

    @JoinColumn(nullable = false)
    @ManyToOne(cascade = CascadeType.ALL)
    private Member member;

}
