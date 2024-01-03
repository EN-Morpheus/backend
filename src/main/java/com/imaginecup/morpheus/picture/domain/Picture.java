package com.imaginecup.morpheus.picture.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "pictures")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Picture {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @Lob
    private String url;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    @Lob
    private String thumbnailUrl;
}
