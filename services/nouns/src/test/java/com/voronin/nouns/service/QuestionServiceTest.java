package com.voronin.nouns.service;

import com.voronin.nouns.domain.Noun;
import com.voronin.nouns.domain.Question;
import com.voronin.nouns.repository.QuestionRepository;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;

/**
 * QuestionService test.
 *
 * @author Alexey Voronin.
 * @since 03.09.2019.
 */
public class QuestionServiceTest {
    /**
     * Mock QuestRepository.
     */
    private QuestionRepository questionRepository = mock(QuestionRepository.class);

    /**
     * Mock WordService.
     */
    private NounService nounService = mock(NounService.class);

    /**
     * Mock ExamService.
     */
    private ExamService examService = mock(ExamService.class);

    /**
     * The class object under test.
     */
    private QuestionService questionService = new QuestionService(questionRepository, nounService, examService);

    /**
     * When call save should return saved Question.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenSaveQuestionShouldReturnSavedQuestion() throws Exception {
        Question question = new Question();
        when(questionRepository.save(question)).thenReturn(question);

        assertThat(questionService.save(question), is(question));
        verify(questionRepository, times(1)).save(question);
    }

    /**
     * When call prepareAndSave should return saved question.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenPrepareAndSaveShouldReturnSavedQuestion() throws Exception {
        Noun noun = new Noun();
        noun.setWord("exam");
        List<String> variants = Lists.newArrayList(noun.getWord());
        List<Noun> nouns = Lists.newArrayList(noun);
        Question question = new Question();
        question.setWord(noun);
        question.setNouns(new HashSet<>(nouns));
        when(nounService.getNounByName(anyString())).thenReturn(noun);
        when(nounService.getNounsByNames(anyList())).thenReturn(nouns);
        when(questionRepository.save(any(Question.class))).thenReturn(question);

        assertThat(questionService.prepareAndSave("exam", noun.getWord(), variants), is(question));
        verify(nounService, times(1)).getNounByName(anyString());
        verify(nounService, times(1)).getNounsByNames(anyList());
        verify(questionRepository, times(1)).save(any(Question.class));
    }
}
