package com.imaginecup.morpheus.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.imaginecup.morpheus.picture.dao.PictureRepository;
import com.imaginecup.morpheus.picture.domain.Picture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class S3ServiceImpl implements S3Service{

    private final PictureRepository pictureRepository;

    @Override
    public Picture uploadMedia(MultipartFile media) {
        return null;
    }

    @Override
    public void deleteFile(String fileName) {

    }

    @Override
    public String getFileExtension(String fileName) {
        return null;
    }
}
