package com.voronin.sentence.service;

import com.voronin.sentence.domain.Exam;
import com.voronin.sentence.domain.KeyWord;
import com.voronin.sentence.domain.Question;
import com.voronin.sentence.repository.QuestionRepository;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * QuestionService test.
 *
 * @author Alexey Voronin.
 * @since 11.10.2019.
 */
public class QuestionServiceTest {

    /**
     * QuestionRepository mock.
     */
    private final QuestionRepository questionRepository = mock(QuestionRepository.class);

    /**
     * Exam Service mock.
     */
    private final ExamService examService = mock(ExamService.class);

    /**
     * KeyWord Service mock.
     */
    private final KeyWordService keyWordService = mock(KeyWordService.class);

    /**
     * The class object under test.
     */
    private QuestionService questionService = new QuestionService(questionRepository, examService, keyWordService);

    /**
     * When call save should return saved question.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenSaveShouldReturnSavedQuestion() throws Exception {
        final String question = "question";
        final String answer = "answer";
        final List<String> strings = Lists.newArrayList("word");
        final List<KeyWord> keyWords = Lists.newArrayList(new KeyWord(UUID.randomUUID(), "word"));
        final String examId = UUID.randomUUID().toString();
        final Question qu = new Question(question, answer);
        final Exam exam = new Exam("exam", Exam.ExamType.PAST);
        qu.setExam(new Exam());
        qu.setKeyWords(keyWords);

        when(this.examService.getExamById(any(UUID.class))).thenReturn(exam);
        when(this.keyWordService.getKeyWordsIn(strings)).thenReturn(keyWords);
        when(this.questionRepository.save(any(Question.class))).thenReturn(qu);

        assertThat(this.questionService.save(question, answer, strings, examId), is(qu));
    }
}
