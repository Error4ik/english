package com.voronin.english.service;

import com.voronin.english.domain.Translation;
import com.voronin.english.domain.Word;
import com.voronin.english.repository.TranslationRepository;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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
@WebMvcTest(TranslationService.class)
@WithMockUser(username = "user", roles = {"USER"})
public class TranslationServiceTest {

    @Autowired
    private TranslationService translationService;

    @MockBean
    private TranslationRepository translationRepository;

    @Test
    public void saveAll() throws Exception {
        Translation translation = new Translation("translation", new Word());
        List<Translation> list = Lists.newArrayList(translation);

        when(translationRepository.saveAll(list)).thenReturn(list);

        assertThat(translationService.saveAll(list), is(list));
    }
}