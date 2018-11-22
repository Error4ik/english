package com.voronin.english.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.voronin.english.domain.Image;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 21.11.2018.
 */
@Service
public class AmazonClient {

    private final String fileSep = "/";

    private AmazonS3 s3client;

    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;
    @Value("${amazonProperties.bucketName}")
    private String bucketName;
    @Value("${amazonProperties.accessKey}")
    private String accessKey;
    @Value("${amazonProperties.secretKey}")
    private String secretKey;
    @Value("${file.extension.to.save}")
    private String fileExtension;

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3client = new AmazonS3Client(credentials);
    }

    public Image uploadFile(final MultipartFile photo, final String dir) throws IOException {
        File file = this.convertFile(photo);
        s3client.putObject(new PutObjectRequest(String.format("%s%s%s", bucketName, fileSep, dir), file.getName(), file)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        Image image = new Image(file.getName(),
                String.format("%s%s%s%s%s%s%s", endpointUrl, fileSep, bucketName, fileSep, dir, fileSep, file.getName()));
        file.delete();
        return image;
    }

    private File convertFile(final MultipartFile file) {
        String name = file.getOriginalFilename().replace(".jpg", "").trim();
        File convFile = new File(String.format("%s-%s.%s", name, System.currentTimeMillis(), fileExtension));
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convFile;
    }
}
