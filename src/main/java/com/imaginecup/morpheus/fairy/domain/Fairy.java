package com.imaginecup.morpheus.fairy.domain;

import com.imaginecup.morpheus.member.domain.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

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

    @ColumnDefault("true")
    private boolean isPublic;

    @Embedded
    private FairyInfo fairyInfo;

}
