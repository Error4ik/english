package com.voronin.english.service;

import com.voronin.english.domain.Category;
import com.voronin.english.domain.Exam;
import com.voronin.english.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 10.12.2018.
 */
@Service
public class ExamService {

    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private CategoryService categoryService;

    public Exam getExamById(final UUID uuid) {
        return this.examRepository.getExamById(uuid);
    }

    public List<Exam> getExams() {
        return this.examRepository.findAll();
    }

    public Exam getExamByCategory(final Category category) {
        return this.examRepository.getExamByCategory(category);
    }

    public Exam getExamByName(final String name) {
        return examRepository.getExamByName(name);
    }

    public Exam save(final Exam exam) {
        return this.examRepository.save(exam);
    }

    public Exam prepareAndSave(final String examName, final String categoryName, final int type) {
        Category category = this.categoryService.getCategoryByName(categoryName);
        Exam exam = new Exam(examName, category);
        exam.setType(type);
        return this.save(exam);
    }
}
