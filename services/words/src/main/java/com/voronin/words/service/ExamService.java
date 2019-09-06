package com.voronin.words.service;

import com.voronin.words.domain.Exam;
import com.voronin.words.domain.PartOfSpeech;
import com.voronin.words.repository.ExamRepository;
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
 * @since 06.09.2019.
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
     * PartOfSpeech service.
     */
    private PartOfSpeechService partOfSpeechService;

    /**
     * Constructor.
     *
     * @param examRepository  exam repository.
     * @param partOfSpeechService partOfSpeech service.
     */
    @Autowired
    public ExamService(final ExamRepository examRepository, final PartOfSpeechService partOfSpeechService) {
        this.examRepository = examRepository;
        this.partOfSpeechService = partOfSpeechService;
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
     * Get exam by partOfSpeech.
     *
     * @param partOfSpeech category.
     * @return Exam.
     */
    public Exam getExamByPartOfSpeech(final PartOfSpeech partOfSpeech) {
        return this.examRepository.getExamByPartOfSpeech(partOfSpeech);
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
     * @param speech part of speech.
     * @param type         exam type.
     * @return Exam.
     */
    public Exam prepareAndSave(final String examName, final String speech, final int type) {
        logger.debug(String.format(
                "Arguments - examName - %s, partOfSpeech - %s, type - %s",
                examName,
                speech,
                type));
        PartOfSpeech partOfSpeech = this.partOfSpeechService.getPartOfSpeechByName(speech);
        Exam exam = new Exam(examName, partOfSpeech, type);
        this.save(exam);
        logger.debug(String.format("Return - %s", exam));
        return exam;
    }
}
