package com.voronin.english.service;

import com.voronin.english.domain.Category;
import com.voronin.english.domain.Exam;
import com.voronin.english.repository.ExamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Exam service.
 *
 * @author Alexey Voronin.
 * @since 10.12.2018.
 */
@Service
public class ExamService {

    /**
     * Logger.
     */
    private final Logger logger = LoggerFactory.getLogger(ExamService.class);

    /**
     * Exam repository.
     */
    private final ExamRepository examRepository;

    /**
     * Category service.
     */
    private CategoryService categoryService;

    /**
     * Constructor.
     *
     * @param examRepository  exam repository.
     * @param categoryService category service.
     */
    @Autowired
    public ExamService(final ExamRepository examRepository, final CategoryService categoryService) {
        this.examRepository = examRepository;
        this.categoryService = categoryService;
    }

    /**
     * Return exam by id.
     *
     * @param uuid id.
     * @return Exam.
     */
    public Exam getExamById(final UUID uuid) {
        return this.examRepository.getExamById(uuid);
    }

    /**
     * Get all exams.
     *
     * @return the list of examinations.
     */
    public List<Exam> getExams() {
        return this.examRepository.findAll();
    }

    /**
     * Get exam by category.
     *
     * @param category category.
     * @return Exam.
     */
    public Exam getExamByCategory(final Category category) {
        return this.examRepository.getExamByCategory(category);
    }

    /**
     * Get Exam by name.
     *
     * @param name name.
     * @return Exam.
     */
    public Exam getExamByName(final String name) {
        return examRepository.getExamByName(name);
    }

    /**
     * Save exam to database.
     *
     * @param exam exam.
     * @return Exam.
     */
    public Exam save(final Exam exam) {
        return this.examRepository.save(exam);
    }

    /**
     * Prepare and save exam to database.
     *
     * @param examName     exam name.
     * @param categoryName category name.
     * @param type         exam type.
     * @return Exam.
     */
    public Exam prepareAndSave(final String examName, final String categoryName, final int type) {
        logger.debug(String.format(
                "Arguments - examName - %s, categoryName - %s, type - %s",
                examName,
                categoryName,
                type));
        Category category = this.categoryService.getCategoryByName(categoryName);
        Exam exam = new Exam(examName, category, type);
        this.save(exam);
        logger.debug(String.format("Return - %s", exam));
        return exam;
    }
}
