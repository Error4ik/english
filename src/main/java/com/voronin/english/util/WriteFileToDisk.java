package com.voronin.english.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 22.10.2018.
 */
@Component
public class WriteFileToDisk {

    private final String fileSeparator = System.getProperty("file.separator");

    @Value("${upload.image.folder}")
    private String pathToSaveImage;

    @Value("${file.extension.to.save}")
    private String fileExtension;

    public File writeImage(final MultipartFile photo) {
        File dir = new File(pathToSaveImage);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String path = String.format("%s%s%s-%s.%s",
                dir,
                fileSeparator,
                photo.getOriginalFilename().replace(".jpg", ""),
                System.currentTimeMillis(),
                fileExtension
        );

        File file = new File(path);
        try {
//            photo.transferTo(file);
            ImageIO.write(ImageIO.read(photo.getInputStream()), fileExtension, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
