package com.imaginecup.morpheus.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.imaginecup.morpheus.picture.dao.PictureRepository;
import com.imaginecup.morpheus.picture.domain.Picture;
import com.imaginecup.morpheus.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

@RequiredArgsConstructor
@Transactional
@Service
public class S3ServiceImpl implements S3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;
    private final AmazonS3Client amazonS3Client;
    private final PictureRepository pictureRepository;

    @Override
    public Picture getImage(MultipartFile photo, String name) {
        try {
            String fileName = String.format("Character %s for %s", name, SecurityUtils.getCurrentMemberId());
            String thumbnailFileName = "thumbnail_" + fileName;

            String photoUrl=uploadOriginImage(photo, fileName);
            String thumbnailUrl= uploadThumbnailImage(photo, thumbnailFileName);

            // 동시에 해당 미디어 파일들을 미디어 DB에 이름과 Url 정보 저장.
            // 게시글마다 어떤 미디어 파일들을 포함하고 있는지 파악하기 위함 또는 활용하기 위함.
            Picture uploadMedia = Picture.builder()
                    .fileName(fileName)
                    .url(photoUrl)
                    .thumbnailUrl(thumbnailUrl)
                    .build();

            pictureRepository.save(uploadMedia);

            return uploadMedia;
        } catch (AmazonS3Exception e) {
            throw new AmazonS3Exception("S3에 저장하는 과정 중 문제가 발생했습니다.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteImage(String url) {
        try {
            URL s3Url = new URL(url);

            // URL에서 키 추출 (경로 부분)
            String key = s3Url.getPath().substring(1); // Remove the leading '/'

            // S3에서 파일 삭제
            amazonS3Client.deleteObject(bucket, key);
        } catch (MalformedURLException e) {
            throw new RuntimeException("잘못된 URL 형식입니다.", e);
        } catch (AmazonS3Exception e) {
            throw new AmazonS3Exception("S3에서 파일을 삭제하는 과정 중 문제가 발생했습니다.");
        }
    }


    private String uploadOriginImage(MultipartFile picture, String fileName) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(picture.getContentType());
        metadata.setContentLength(picture.getSize());

        // 이미지 원본 저장
        amazonS3Client.putObject(bucket, fileName, picture.getInputStream(), metadata);

        return amazonS3.getUrl(bucket, fileName).toString();
    }

    private String uploadThumbnailImage(MultipartFile picture, String fileName) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Thumbnails.of(picture.getInputStream()).size(100, 100).toOutputStream(os);
        byte[] thumbnailBytes = os.toByteArray();
        InputStream is = new ByteArrayInputStream(thumbnailBytes);

        ObjectMetadata thumbMetadata = new ObjectMetadata();
        thumbMetadata.setContentType(picture.getContentType());
        thumbMetadata.setContentLength(thumbnailBytes.length);

        amazonS3Client.putObject(bucket, fileName, is, thumbMetadata);

        return amazonS3.getUrl(bucket, fileName).toString();
    }

}
