package com.voronin.english.service;

import com.voronin.english.domain.Question;
import com.voronin.english.domain.Word;
import com.voronin.english.repository.QuestRepository;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

/**
 * QuestionService test class.
 *
 * @author Alexey Voronin.
 * @since 19.12.2018.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(QuestionService.class)
@WithMockUser(username = "user", roles = {"USER"})
public class QuestionServiceTest {

    /**
     * The class object under test.
     */
    @Autowired
    private QuestionService questionService;

    /**
     * Mock QuestRepository.
     */
    @MockBean
    private QuestRepository questRepository;

    /**
     * Mock WordService.
     */
    @MockBean
    private WordService wordService;

    /**
     * Mock ExamService.
     */
    @MockBean
    private ExamService examService;

    /**
     * When call save should return saved Question.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenSaveQuestionShouldReturnSavedQuestion() throws Exception {
        Question question = new Question();
        when(questRepository.save(question)).thenReturn(question);

        assertThat(questionService.save(question), is(question));
    }

    /**
     * When call prepareAndSave should return saved question.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenPrepareAndSaveShouldReturnSavedQuestion() throws Exception {
        Word word = new Word();
        word.setWord("exam");
        List<String> variants = Lists.newArrayList(word.getWord());
        List<Word> words = Lists.newArrayList(word);
        Question question = new Question();
        question.setWord(word);
        question.setWords(new HashSet<>(words));
        when(wordService.getWordByName(anyString())).thenReturn(word);
        when(wordService.getWordsByNames(anyList())).thenReturn(words);
        when(wordService.save(any(Word.class))).thenReturn(word);
        when(questRepository.save(any(Question.class))).thenReturn(question);

        assertThat(questionService.prepareAndSave("exam", word.getWord(), variants), is(question));
    }
}
