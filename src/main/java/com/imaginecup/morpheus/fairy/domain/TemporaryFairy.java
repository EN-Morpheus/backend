package com.imaginecup.morpheus.fairy.domain;

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

    @Embedded
    private FairyInfo fairyInfo;

}
