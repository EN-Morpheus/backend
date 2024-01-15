package com.imaginecup.morpheus.chapter.service;

import com.imaginecup.morpheus.chapter.dao.ChapterRepository;
import com.imaginecup.morpheus.chapter.domain.Chapter;
import com.imaginecup.morpheus.chapter.dto.response.ChapterDto;
import com.imaginecup.morpheus.chapter.dto.response.Chapters;
import com.imaginecup.morpheus.fairy.domain.TemporaryFairy;
import com.imaginecup.morpheus.picture.domain.Picture;
import com.imaginecup.morpheus.picture.service.PictureService;
import com.imaginecup.morpheus.s3.service.S3Service;
import com.imaginecup.morpheus.utils.ImageSaver;
import com.imaginecup.morpheus.utils.Parser;
import com.imaginecup.morpheus.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChapterServiceImpl implements ChapterService {

    private final ChapterRepository chapterRepository;
    private final S3Service s3Service;
    private final PictureService pictureService;


    @Override
    public Chapters saveChaptersJsonObject(Long temporaryFairyId, JSONObject json) {
        Chapters chapters = Chapters.builder()
                .temporaryFairyId(temporaryFairyId)
                .chapters(Parser.convertJsonObject(json))
                .build();

        return chapters;
    }

    @Override
    public Chapters saveChaptersJsonArray(Long temporaryFairyId, JSONObject json) {
        return Chapters.builder()
                .temporaryFairyId(temporaryFairyId)
                .chapters(Parser.convertJsonArray(json))
                .build();
    }

    @Override
    public void saveFirstTemporary(TemporaryFairy temporaryFairy, List<ChapterDto> chapters) {
        for (ChapterDto chapterDto : chapters) {
            Chapter chapter = Chapter.builder()
                    .plot(chapterDto.getPlot())
                    .background(chapterDto.getBackground())
                    .story(chapterDto.getStory())
                    .order(chapterDto.getOrder())
                    .image(null)
                    .narrativeText(chapterDto.getNarrativeText())
                    .temporaryFairy(temporaryFairy)
                    .build();
            chapterRepository.save(chapter);
        }
    }

    @Override
    public List<Chapter> updateTemporary(List<Chapter> chapters, List<ChapterDto> chapterDtos) throws Exception {
        Long temporaryId = chapters.get(0).getTemporaryFairy().getId();

        for (int i = 0; i < chapters.size(); i++) {
            Picture picture = saveChapterImage(chapterDtos.get(i).getImageUrl(), i + 1, temporaryId);
            chapters.get(i).setImage(picture);
        }
        return chapters;
    }

    @Override
    public void deleteChapter(Long temporaryId) {
        List<Chapter> chapters = chapterRepository.findByTemporaryFairyId(temporaryId);

        for (Chapter chapter : chapters) {
            if (chapter.getImage() == null) {
                continue;
            }
            pictureService.deletePicture(chapter.getImage());
            chapterRepository.delete(chapter);
        }
    }

    private Picture saveChapterImage(String imageUrl, int order, Long temporaryId) throws Exception {
        Picture picture = null;
        if (imageUrl == null) {
            return picture;
        }

        MultipartFile imageFile = ImageSaver.downloadImageAsMultipartFile(imageUrl);
        String fileName = createChapterImageName(order, temporaryId);
        picture = s3Service.getImage(imageFile, fileName);

        return picture;
    }

    private String createChapterImageName(int order, Long temporaryId) {
        return String.format("The %d page of %s's %d temporary save fairy tale",
                order, SecurityUtils.getCurrentMemberId(), temporaryId);
    }

}
