package com.voronin.english.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final static Logger LOGGER = LoggerFactory.getLogger(WriteFileToDisk.class);

    private final String fileSeparator = System.getProperty("file.separator");

    @Value("${file.extension.to.save}")
    private String fileExtension;

    public File writeImage(final MultipartFile photo, final String pathToSaveImage) {
        File dir = new File(pathToSaveImage);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String path = String.format("%s%s%s-%s.%s", dir, fileSeparator,
                photo.getOriginalFilename().replace(".png", ""),
                System.currentTimeMillis(), fileExtension
        );

        File file = new File(path);
        try {
//            photo.transferTo(file);
            ImageIO.write(ImageIO.read(photo.getInputStream()), fileExtension, file);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return file;
    }
}
