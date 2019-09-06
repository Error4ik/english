package com.voronin.words.service;

import com.voronin.words.domain.Question;
import com.voronin.words.domain.Word;
import com.voronin.words.repository.QuestionRepository;
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
 * @since 06.09.2019.
 */
public class QuestionServiceTest {

    /**
     * Mock QuestRepository.
     */
    private QuestionRepository questionRepository = mock(QuestionRepository.class);

    /**
     * Mock WordService.
     */
    private WordService wordService = mock(WordService.class);

    /**
     * Mock ExamService.
     */
    private ExamService examService = mock(ExamService.class);

    /**
     * The class object under test.
     */
    private QuestionService questionService = new QuestionService(questionRepository, wordService, examService);

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
        Word word = new Word();
        word.setWord("word");
        List<String> variants = Lists.newArrayList(word.getWord());
        List<Word> words = Lists.newArrayList(word);
        Question question = new Question();
        question.setWord(word);
        question.setWords(new HashSet<>(words));
        when(wordService.getWordByWord(anyString())).thenReturn(word);
        when(wordService.getWordByNames(anyList())).thenReturn(words);
        when(questionRepository.save(any(Question.class))).thenReturn(question);

        assertThat(questionService.prepareAndSave("exam", word.getWord(), variants), is(question));
//        verify(wordService, times(1)).getWordByWord(anyString());
//        verify(wordService, times(1)).getWordByNames(anyList());
        verify(questionRepository, times(1)).save(any(Question.class));
    }
}
