package com.voronin.english.service;

import com.google.common.collect.Lists;
import com.voronin.english.domain.Question;
import com.voronin.english.domain.Word;
import com.voronin.english.repository.QuestRepository;
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
 * @since 10.12.2018.
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
    private final QuestRepository questRepository;

    /**
     * Word service.
     */
    private final WordService wordService;

    /**
     * Exam service.
     */
    private final ExamService examService;

    /**
     * Constructor.
     *
     * @param questRepository question repository.
     * @param wordService     word service.
     * @param examService     exam service.
     */
    @Autowired
    public QuestionService(
            final QuestRepository questRepository,
            final WordService wordService,
            final ExamService examService) {
        this.questRepository = questRepository;
        this.wordService = wordService;
        this.examService = examService;
    }

    /**
     * Save question.
     *
     * @param question question.
     * @return question.
     */
    public Question save(final Question question) {
        return this.questRepository.save(question);
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
        Word correctAnswer = this.wordService.getWordByName(word);
        Set<Word> answerChoice = new HashSet<>(Lists.newArrayList(this.wordService.getWordsByNames(variants)));
        answerChoice.add(correctAnswer);
        Question question = this.save(new Question(correctAnswer, this.examService.getExamByName(exam), answerChoice));
        logger.debug(String.format("Return - %s", question));
        return question;
    }
}
