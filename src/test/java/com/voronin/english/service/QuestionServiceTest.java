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
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 19.12.2018.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(QuestionService.class)
@WithMockUser(username = "user", roles = {"USER"})
public class QuestionServiceTest {

    @Autowired
    private QuestionService questionService;

    @MockBean
    private QuestRepository questRepository;
    @MockBean
    private WordService wordService;
    @MockBean
    private ExamService examService;

    @Test
    public void whenSaveQuestionShouldReturnSavedQuestion() throws Exception {
        Question question = new Question();
        when(questRepository.save(question)).thenReturn(question);

        assertThat(questionService.save(question), is(question));
    }

    @Test
    public void whenPrepareAndSaveShouldReturnSavedQuestion() throws Exception {
        Word word = new Word();
        word.setWord("exam");
        List<String> variants = Lists.newArrayList(word.getWord());
        List<Word> words = Lists.newArrayList(word);
        word.setUseInQuestion(true);
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