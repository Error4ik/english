package com.voronin.english.service;

import com.voronin.english.domain.Category;
import com.voronin.english.domain.Image;
import com.voronin.english.repository.CategoryRepository;
import com.voronin.english.util.WriteFileToDisk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
    private WriteFileToDisk writeFileToDisk;

    @Autowired
    private ImageService imageService;

    @Value("${upload.image.category.folder}")
    private String pathToSaveImage;

    public List<Category> getCategories() {
        return this.categoryRepository.findAll();
    }

    public Category getCategoryByName(final String name) {
        return this.categoryRepository.getCategoryByName(name);
    }

    public Category prepareAndSave(final Category category, final MultipartFile photo) {
        File file = this.writeFileToDisk.writeImage(photo, pathToSaveImage);
        category.setImage(this.imageService.save(new Image(file.getName(), file.getAbsolutePath())));
        return this.categoryRepository.save(category);
    }

    public Category save(final Category category) {
        return this.categoryRepository.save(category);
    }
}
