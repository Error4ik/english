package com.voronin.english.service;

import com.voronin.english.domain.Phrase;
import com.voronin.english.repository.PhraseRepository;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 30.11.2018.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PhraseService.class)
@WithMockUser(username = "user", roles = {"USER"})
public class PhraseServiceTest {

    @Autowired
    private PhraseService phraseService;

    @MockBean
    private PhraseRepository phraseRepository;

    @Test
    public void saveAll() throws Exception {
        Phrase phrase = new Phrase();
        phrase.setId(UUID.randomUUID());
        phrase.setPhrase("Test Phrase");
        List<Phrase> list = Lists.newArrayList(phrase);
        when(phraseRepository.saveAll(list)).thenReturn(list);

        assertThat(phraseService.saveAll(list), is(list));
    }
}
