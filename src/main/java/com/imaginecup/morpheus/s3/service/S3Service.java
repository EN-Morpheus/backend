package com.imaginecup.morpheus.s3.service;

import com.imaginecup.morpheus.picture.domain.Picture;
import org.springframework.web.multipart.MultipartFile;

public interface S3Service {

    Picture uploadMedia(MultipartFile media);

    void deleteFile(String fileName);

    String getFileExtension(String fileName);

}
