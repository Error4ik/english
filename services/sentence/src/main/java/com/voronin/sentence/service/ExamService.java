package com.voronin.sentence.service;

import com.voronin.sentence.domain.Exam;
import com.voronin.sentence.repository.ExamRepository;
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
 * @since 04.10.2019.
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
     * Constructor.
     *
     * @param examRepository exam repository.
     */
    @Autowired
    public ExamService(final ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    /**
     * GetExam by id.
     *
     * @param examId Exam id.
     * @return Exam.
     */
    public Exam getExamById(final UUID examId) {
        return this.examRepository.getExamById(examId);
    }

    /**
     * Get exams.
     *
     * @return list of exams.
     */
    public List<Exam> getExams() {
        return examRepository.findAll();
    }

    /**
     * Save exam.
     *
     * @param examName exam name.
     * @param examType exam type.
     * @return saved exam.
     */
    public Exam save(final String examName, final String examType) {
        logger.debug(String.format("Arguments: {examName=%s, examType=%s}", examName, examType));
        Exam.ExamType type = Exam.ExamType.FUTURE;
        for (Exam.ExamType value : Exam.ExamType.values()) {
            if (value.toString().equalsIgnoreCase(examType)) {
                type = value;
            }
        }
        Exam exam = new Exam(examName, type);
        this.examRepository.save(exam);
        logger.debug(String.format("Return: %s", exam));
        return exam;
    }
}
