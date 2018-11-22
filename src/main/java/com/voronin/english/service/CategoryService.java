package com.voronin.english.service;

import com.voronin.english.domain.Category;
import com.voronin.english.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 10.10.2018.
 */
@Service
public class CategoryService {

    private final static Logger LOGGER = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private AmazonClient amazonClient;

    @Value("${upload.image.category.folder}")
    private String pathToSaveImage;

    public List<Category> getCategories() {
        return this.categoryRepository.findAll();
    }

    public Category getCategoryByName(final String name) {
        return this.categoryRepository.getCategoryByName(name);
    }

    public void prepareAndSave(final Category category, final MultipartFile photo) {
        try {
            category.setImage(this.imageService.save(this.amazonClient.uploadFile(photo, pathToSaveImage)));
            this.categoryRepository.save(category);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
