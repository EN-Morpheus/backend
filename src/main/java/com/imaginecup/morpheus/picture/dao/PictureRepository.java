package com.imaginecup.morpheus.picture.dao;

import com.imaginecup.morpheus.picture.domain.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture, Long> {
}
