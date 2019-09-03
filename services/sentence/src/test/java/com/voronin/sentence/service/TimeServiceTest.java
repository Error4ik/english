package com.voronin.sentence.service;

import com.voronin.sentence.domain.Time;
import com.voronin.sentence.repository.TimeRepository;
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
 * TimeService test class.
 *
 * @author Alexey Voronin.
 * @since 04.02.2019.
 */
public class TimeServiceTest {

    /**
     * Mock TimeRepository.
     */
    @MockBean
    private TimeRepository timeRepository = mock(TimeRepository.class);

    /**
     * The class object under test.
     */
    private TimeService timeService = new TimeService(timeRepository);

    /**
     * When call save method should return saved Time.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenCallSaveMethodShouldReturnSavedTime() throws Exception {
        final Time time = new Time("phrase");
        when(timeRepository.save(time)).thenReturn(time);

        assertThat(this.timeService.save(time), is(time));
        verify(timeRepository, times(1)).save(time);
    }

    /**
     * When call findAll method should return list of Time.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenCallFindAllShouldReturnListOfTime() throws Exception {
        final List<Time> list = Lists.newArrayList(new Time("phrase"));
        when(this.timeRepository.findAll()).thenReturn(list);

        assertThat(this.timeService.findAll(), is(list));
        verify(timeRepository, times(1)).findAll();
    }
}
