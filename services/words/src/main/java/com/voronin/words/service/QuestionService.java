package com.voronin.words.service;

import com.voronin.words.domain.Question;
import com.voronin.words.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Question service.
 *
 * @author Alexey Voronin.
 * @since 06.09.2019.
 */
@Service
public class QuestionService {

    /**
     * Question repository.
     */
    private QuestionRepository questionRepository;

    /**
     * Noun service.
     */
    private final WordService wordService;

    /**
     * Exam service.
     */
    private final ExamService examService;

    /**
     * Constructor.
     *
     * @param questionRepository question repository.
     * @param wordService        WordService.
     * @param examService        ExamService.
     */
    @Autowired
    public QuestionService(
            final QuestionRepository questionRepository,
            final WordService wordService,
            final ExamService examService) {
        this.questionRepository = questionRepository;
        this.wordService = wordService;
        this.examService = examService;
    }

    /**
     * Save question.
     *
     * @param question question.
     * @return saved question.
     */
    public Question save(final Question question) {
        return this.questionRepository.save(question);
    }

    /**
     * Prepare question and save.
     *
     * @param exam     exam name.
     * @param word     correct answer.
     * @param variants answer choice.
     * @return Question.
     */
    public Question prepareAndSave(final String exam, final String word, final List<String> variants) {
        Question question = new Question();
        return this.save(question);
    }
}
