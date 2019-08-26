package com.voronin.nouns.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Class for writing a file to disk.
 *
 * @author Alexey Voronin.
 * @since 22.10.2018.
 */
@Component
public class WriteFileToDisk {

    /**
     * Logger.
     */
    private final Logger logger = LoggerFactory.getLogger(WriteFileToDisk.class);

    /**
     * File separator.
     */
    private final String fileSeparator = System.getProperty("file.separator");

    /**
     * Image extension.
     */
    @Value("${file.extension.to.save}")
    private String fileExtension;

    /**
     * Write image to disk.
     *
     * @param photo           image.
     * @param pathToSaveImage path to save.
     * @param imageName       name for image.
     * @return File.
     */
    public File writeImage(final MultipartFile photo, final String pathToSaveImage, final String imageName) {
        File dir = new File(pathToSaveImage);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String path = String.format(
                "%s%s%s-%s.%s",
                dir,
                fileSeparator,
                imageName,
                System.currentTimeMillis(),
                fileExtension
        );

        File file = new File(path);
        try {
            ImageIO.write(ImageIO.read(photo.getInputStream()), fileExtension, file);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return file;
    }
}
