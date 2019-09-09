package com.voronin.words.service;

import com.google.common.collect.Lists;
import com.voronin.words.domain.Question;
import com.voronin.words.domain.Word;
import com.voronin.words.repository.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Question service.
 *
 * @author Alexey Voronin.
 * @since 06.09.2019.
 */
@Service
public class QuestionService {

    /**
     * Logger.
     */
    private final Logger logger = LoggerFactory.getLogger(QuestionService.class);

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
        logger.debug(String.format("Arguments - exam - %s, word - %s, variants - %s", exam, word, variants));
        Word correctAnswer = this.wordService.getWordByWord(word);
        Set<Word> answerChoice = new HashSet<>(Lists.newArrayList(this.wordService.getWordByNames(variants)));
        answerChoice.add(correctAnswer);
        Question question = this.save(new Question(correctAnswer, answerChoice, this.examService.getExamByName(exam)));
        logger.debug(String.format("Return - %s", question));
        return question;
    }
}
