package com.voronin.english.service;

import com.voronin.english.domain.TimeOfPhrase;
import com.voronin.english.repository.TimeOfPhraseRepository;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * TimeOfPhraseService test class.
 *
 * @author Alexey Voronin.
 * @since 04.02.2019.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(TimeOfPhraseService.class)
@WithMockUser(username = "user", roles = {"USER"})
public class TimeOfPhraseServiceTest {

    /**
     * The class object under test.
     */
    @Autowired
    private TimeOfPhraseService timeOfPhraseService;

    /**
     * Mock JavaMailSender.
     */
    @MockBean
    private JavaMailSender javaMailSender;

    /**
     * Mock TimeOfPhraseRepository.
     */
    @MockBean
    private TimeOfPhraseRepository timeOfPhraseRepository;

    /**
     * When call save method should return saved entity.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenCallSaveMethodShouldReturnSavedEntity() throws Exception {
        final TimeOfPhrase timeOfPhrase = new TimeOfPhrase("phrase");
        when(timeOfPhraseRepository.save(timeOfPhrase)).thenReturn(timeOfPhrase);

        assertThat(this.timeOfPhraseService.save(timeOfPhrase), is(timeOfPhrase));
    }

    /**
     * When call findAll method should return list of entity.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenCallFindAllShouldReturnListOfEntity() throws Exception {
        final List<TimeOfPhrase> list = Lists.newArrayList(new TimeOfPhrase("phrase"));
        when(this.timeOfPhraseRepository.findAll()).thenReturn(list);

        assertThat(this.timeOfPhraseService.findAll(), is(list));
    }
}
