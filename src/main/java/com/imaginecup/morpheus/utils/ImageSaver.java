package com.imaginecup.morpheus.utils;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

public class ImageSaver {

    public static MultipartFile downloadImageAsMultipartFile(String imageUrl) throws Exception {
        URL url = new URL(imageUrl);
        try (InputStream in = url.openStream(); ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }

            // Use the original file name, or a custom name as per your requirement
            String filename = "downloaded_image";
            // You can set a specific content type, or use the default
            String contentType = "image/png";

            // Creating the MultipartFile from the byte array
            return new MockMultipartFile(filename, filename, contentType, bos.toByteArray());
        }
    }

}
