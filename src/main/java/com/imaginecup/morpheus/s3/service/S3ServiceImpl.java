package com.imaginecup.morpheus.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.imaginecup.morpheus.picture.dao.PictureRepository;
import com.imaginecup.morpheus.picture.domain.Picture;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Transactional
@Service
public class S3ServiceImpl implements S3Service{

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;
    private final AmazonS3Client amazonS3Client;
    private final PictureRepository pictureRepository;

    @Override
    public Picture uploadMedia(MultipartFile photo, String name) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(photo.getContentType());
            metadata.setContentLength(photo.getSize());
            amazonS3Client.putObject(bucket, name, photo.getInputStream(), metadata);
        } catch (AmazonS3Exception e) {
            throw new AmazonS3Exception("S3에 저장하는 과정 중 문제가 발생했습니다.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 접근가능한 URL 가져오기
        String photoUrl = amazonS3.getUrl(bucket, photo.getOriginalFilename()).toString();

        // 동시에 해당 미디어 파일들을 미디어 DB에 이름과 Url 정보 저장.
        // 게시글마다 어떤 미디어 파일들을 포함하고 있는지 파악하기 위함 또는 활용하기 위함.
        Picture uploadMedia = Picture.builder()
                .originFileName(photo.getOriginalFilename())
                .url(photoUrl)
                .build();

        pictureRepository.save(uploadMedia);

        return uploadMedia;
    }

}
