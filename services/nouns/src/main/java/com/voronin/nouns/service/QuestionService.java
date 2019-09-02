package com.voronin.nouns.service;

import com.google.common.collect.Lists;
import com.voronin.nouns.domain.Noun;
import com.voronin.nouns.domain.Question;
import com.voronin.nouns.repository.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Question Service.
 *
 * @author Alexey Voronin.
 * @since 30.08.2019.
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
    private final QuestionRepository questionRepository;

    /**
     * Noun service.
     */
    private final NounService nounService;

    /**
     * Exam service.
     */
    private final ExamService examService;

    /**
     * Constructor.
     *
     * @param questionRepository question repository.
     * @param nounService        noun service.
     * @param examService        exam service.
     */
    @Autowired
    public QuestionService(
            final QuestionRepository questionRepository,
            final NounService nounService,
            final ExamService examService) {
        this.questionRepository = questionRepository;
        this.nounService = nounService;
        this.examService = examService;
    }

    /**
     * Save question.
     *
     * @param question question.
     * @return question.
     */
    public Question save(final Question question) {
        return this.questionRepository.save(question);
    }

    /**
     * Prepare question and save.
     *
     * @param exam     exam name.
     * @param noun     correct answer.
     * @param variants answer choice.
     * @return Question.
     */
    public Question prepareAndSave(final String exam, final String noun, final List<String> variants) {
        logger.debug(String.format("Arguments - exam - %s, word - %s, variants - %s", exam, noun, variants));
        Noun correctAnswer = this.nounService.getNounByName(noun);
        Set<Noun> answerChoice = new HashSet<>(Lists.newArrayList(this.nounService.getNounsByNames(variants)));
        answerChoice.add(correctAnswer);
        Question question = this.save(new Question(correctAnswer, this.examService.getExamByName(exam), answerChoice));
        logger.debug(String.format("Return - %s", question));
        return question;
    }
}
