package com.voronin.sentence.service;

import com.voronin.sentence.domain.Question;
import com.voronin.sentence.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * QuestionService.
 *
 * @author Alexey Voronin.
 * @since 08.10.2019.
 */
@Service
public class QuestionService {

    /**
     * QuestionRepository.
     */
    private final QuestionRepository questionRepository;

    /**
     * Exam Service.
     */
    private final ExamService examService;

    /**
     * KeyWord Service.
     */
    private final KeyWordService keyWordService;

    /**
     * Constructor.
     *
     * @param questionRepository QuestionRepository.
     * @param examService        ExamService.
     * @param keyWordService     KeyWordService.
     */
    @Autowired
    public QuestionService(
            final QuestionRepository questionRepository,
            final ExamService examService,
            final KeyWordService keyWordService) {
        this.questionRepository = questionRepository;
        this.examService = examService;
        this.keyWordService = keyWordService;
    }

    /**
     * Save Question.
     *
     * @param question some question.
     * @param answer   answer for question.
     * @param keyWords list of KeyWords.
     * @param examId   Exam id.
     * @return saved Question.
     */
    public Question save(final String question, final String answer, final List<String> keyWords, final String examId) {
        Question saveQuestion = new Question(question, answer);
        saveQuestion.setExam(this.examService.getExamById(UUID.fromString(examId)));
        saveQuestion.setKeyWords(this.keyWordService.getKeyWordsIn(keyWords));
        return this.questionRepository.save(saveQuestion);
    }
}
