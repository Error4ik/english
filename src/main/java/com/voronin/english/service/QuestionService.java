package com.voronin.english.service;

import com.google.common.collect.Lists;
import com.voronin.english.domain.Question;
import com.voronin.english.domain.Word;
import com.voronin.english.repository.QuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 10.12.2018.
 */
@Service
public class QuestionService {

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private WordService wordService;

    @Autowired
    private ExamService examService;

    public Question save(final Question question) {
        return this.questRepository.save(question);
    }

    public Question prepareAndSave(final String exam, final String word, List<String> variants) {
        Word correctAnswer = this.wordService.getWordByName(word);
        Set<Word> answerChoice = new HashSet<>(Lists.newArrayList(this.wordService.getWordsByNames(variants)));
        answerChoice.add(correctAnswer);
        correctAnswer.setUseInQuestion(true);
        return this.save(new Question(wordService.save(correctAnswer),
                this.examService.getExamByName(exam),
                answerChoice));
    }
}
