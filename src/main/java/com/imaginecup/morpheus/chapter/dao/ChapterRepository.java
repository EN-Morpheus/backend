package com.imaginecup.morpheus.chapter.dao;

import com.imaginecup.morpheus.chapter.domain.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
}
