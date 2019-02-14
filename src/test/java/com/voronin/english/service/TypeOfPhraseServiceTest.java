package com.voronin.english.service;

import com.voronin.english.domain.TypeOfPhrase;
import com.voronin.english.repository.TypeOfPhraseRepository;
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
 * TypeOfPhraseService test class.
 *
 * @author Alexey Voronin.
 * @since 04.02.2019.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(TypeOfPhraseService.class)
@WithMockUser(username = "user", roles = {"USER"})
public class TypeOfPhraseServiceTest {

    /**
     * The class object under test.
     */
    @Autowired
    private TypeOfPhraseService typeOfPhraseService;

    /**
     * Mock JavaMailSender.
     */
    @MockBean
    private JavaMailSender javaMailSender;

    /**
     * Mock TypeOfPhraseRepository.
     */
    @MockBean
    private TypeOfPhraseRepository typeOfPhraseRepository;

    /**
     * When call save method should return saved entity.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenCallSaveShouldReturnSavedEntity() throws Exception {
        final TypeOfPhrase typeOfPhrase = new TypeOfPhrase("type");
        when(typeOfPhraseRepository.save(typeOfPhrase)).thenReturn(typeOfPhrase);

        assertThat(this.typeOfPhraseService.save(typeOfPhrase), is(typeOfPhrase));
    }

    /**
     * When call findAll method should return list of entity.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenCallFindAllShouldReturnListOfEntity() throws Exception {
        List<TypeOfPhrase> list = Lists.newArrayList(new TypeOfPhrase("type"));
        when(typeOfPhraseRepository.findAll()).thenReturn(list);

        assertThat(this.typeOfPhraseService.findAll(), is(list));
    }
}
