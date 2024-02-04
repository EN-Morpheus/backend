package com.imaginecup.morpheus.picture.service;

import com.imaginecup.morpheus.chapter.domain.Chapter;
import com.imaginecup.morpheus.picture.dao.PictureRepository;
import com.imaginecup.morpheus.picture.domain.Picture;
import com.imaginecup.morpheus.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;
    private final S3Service s3Service;

    @Override
    public void deletePicture(Picture picture) {
        s3Service.deleteImage(picture.getUrl());
        pictureRepository.delete(picture);
    }

}
