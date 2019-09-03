package com.voronin.sentence.service;

import com.voronin.sentence.domain.Type;
import com.voronin.sentence.repository.TypeRepository;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

/**
 * TypeOfPhraseService test class.
 *
 * @author Alexey Voronin.
 * @since 04.02.2019.
 */
public class TypeServiceTest {

    /**
     * Mock TypeRepository.
     */
    @MockBean
    private TypeRepository typeRepository = mock(TypeRepository.class);

    /**
     * The class object under test.
     */
    private TypeService typeService = new TypeService(typeRepository);

    /**
     * When call save method should return saved Type.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenCallSaveShouldReturnSavedType() throws Exception {
        final Type type = new Type("type");
        when(typeRepository.save(type)).thenReturn(type);

        assertThat(this.typeService.save(type), is(type));
        verify(typeRepository, times(1)).save(type);
    }

    /**
     * When call findAll method should return list of Type.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenCallFindAllShouldReturnListOfEntity() throws Exception {
        List<Type> list = Lists.newArrayList(new Type("type"));
        when(typeRepository.findAll()).thenReturn(list);

        assertThat(this.typeService.findAll(), is(list));
        verify(typeRepository, times(1)).findAll();
    }
}
