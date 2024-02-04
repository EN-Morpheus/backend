package com.imaginecup.morpheus.chapter.domain;

import com.imaginecup.morpheus.fairy.domain.Fairy;
import com.imaginecup.morpheus.fairy.domain.TemporaryFairy;
import com.imaginecup.morpheus.picture.domain.Picture;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "chapters")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String story;

    @Column(nullable = false)
    private String plot;

    @Column(nullable = false)
    private String background;

    @Column(nullable = false)
    private String narrativeText;

    @Column(nullable = false)
    private int chapterOrder;

    @OneToOne
    @JoinColumn(name = "picture_id")
    private Picture image;

    @ManyToOne
    @JoinColumn(name = "fairy_id")
    private Fairy fairy;

    @ManyToOne
    @JoinColumn(name = "temporary_fairy_id")
    private TemporaryFairy temporaryFairy;

}
