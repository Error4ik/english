package com.voronin.nouns.service;


import com.voronin.nouns.domain.Category;
import com.voronin.nouns.domain.Image;
import com.voronin.nouns.repository.CategoryRepository;
import com.voronin.nouns.utils.WriteFileToDisk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.util.List;

/**
 * Category service.
 *
 * @author Alexey Voronin.
 * @since 10.10.2018.
 */
@Service
public class CategoryService {

    /**
     * Logger.
     */
    private final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    /**
     * Category repository.
     */
    private final CategoryRepository categoryRepository;

    /**
     * Class for writing a file to disk.
     */
    private final WriteFileToDisk writeFileToDisk;

    /**
     * Image service.
     */
    private final ImageService imageService;

    /**
     * Default path to save image to disk.
     */
    @Value("${upload.image.category.folder}")
    private String pathToSaveImage;

    /**
     * Constructor.
     *
     * @param categoryRepository category repository.
     * @param writeFileToDisk    class for writing a file to disk.
     * @param imageService       image service.
     */
    @Autowired
    public CategoryService(
            final CategoryRepository categoryRepository,
            final WriteFileToDisk writeFileToDisk,
            final ImageService imageService) {
        this.categoryRepository = categoryRepository;
        this.writeFileToDisk = writeFileToDisk;
        this.imageService = imageService;
    }

    /**
     * Get all category order by name.
     *
     * @return list of category.
     */
    public List<Category> getCategories() {
        return this.categoryRepository.findAllByOrderByNameAsc();
    }

    /**
     * Get category by name.
     *
     * @param name name.
     * @return category.
     */
    public Category getCategoryByName(final String name) {
        return this.categoryRepository.getCategoryByName(name);
    }

    /**
     * Prepare and save category to database.
     *
     * @param category the category that you want to save to the database.
     * @param photo    image for category.
     * @return category.
     */
    @Transactional
    public Category prepareAndSave(final Category category, final MultipartFile photo) {
        logger.debug(String.format("Arguments - %s, %s", category, photo));
        File file = this.writeFileToDisk.writeImage(photo, pathToSaveImage, category.getName());
        category.setImage(this.imageService.save(new Image(file.getName(), file.getAbsolutePath())));
        this.categoryRepository.save(category);
        logger.debug(String.format("Return - %s", category));
        return category;
    }

    /**
     * Save category to database.
     *
     * @param category category.
     * @return category.
     */
    public Category save(final Category category) {
        return this.categoryRepository.save(category);
    }
}
