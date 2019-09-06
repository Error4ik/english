package com.voronin.words.service;

import com.voronin.words.domain.Exam;
import com.voronin.words.domain.PartOfSpeech;
import com.voronin.words.repository.ExamRepository;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

/**
 * Exam service test class.
 *
 * @author Alexey Voronin.
 * @since 06.09.2019.
 */
public class ExamServiceTest {

    /**
     * Mock ExamRepository.
     */
    private ExamRepository examRepository = mock(ExamRepository.class);

    /**
     * Mock PartOfSpeechService.
     */
    private PartOfSpeechService partOfSpeechService = mock(PartOfSpeechService.class);

    /**
     * Class for test.
     */
    private Exam exam = new Exam();

    /**
     * Class for test.
     */
    private PartOfSpeech partOfSpeech = new PartOfSpeech();

    /**
     * The class object under test.
     */
    private ExamService examService = new ExamService(examRepository, partOfSpeechService);

    /**
     * UUID id for test.
     */
    private UUID uuid = UUID.randomUUID();

    /**
     * initialization of objects for the tests.
     */
    @Before
    public void init() {
        partOfSpeech.setId(uuid);
        partOfSpeech.setName("speech");
        exam.setId(uuid);
        exam.setName("exam");
        exam.setPartOfSpeech(partOfSpeech);
        exam.setType(0);
    }

    /**
     * When call getExamById should return exam.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetExamByIdShouldReturnExam() throws Exception {
        when(examRepository.getExamById(uuid)).thenReturn(exam);

        assertThat(examService.getExamById(uuid), is(exam));
        verify(examRepository, times(1)).getExamById(uuid);
    }

    /**
     * When getExams should return list of exam.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetExamsShouldReturnListExam() throws Exception {
        List<Exam> exams = Lists.newArrayList(exam);
        when(examRepository.findAll()).thenReturn(exams);

        assertThat(examService.getExams(), is(exams));
        verify(examRepository, times(1)).findAll();
    }

    /**
     * When call getExamByCategory should return exam.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetExamByCategoryShouldReturnExam() throws Exception {
        when(examRepository.getExamByPartOfSpeech(partOfSpeech)).thenReturn(exam);

        assertThat(examService.getExamByPartOfSpeech(partOfSpeech), is(exam));
        verify(examRepository, times(1)).getExamByPartOfSpeech(partOfSpeech);
    }

    /**
     * When call getExamByName should return exam.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetExamByNameShouldReturnExam() throws Exception {
        when(examRepository.getExamByName(exam.getName())).thenReturn(exam);

        assertThat(examService.getExamByName(exam.getName()), is(exam));
        verify(examRepository, times(1)).getExamByName(exam.getName());
    }

    /**
     * When call save should return saved exam.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenSaveExamShouldReturnSavedExam() throws Exception {
        when(examRepository.save(exam)).thenReturn(exam);

        assertThat(examService.save(exam), is(exam));
        verify(examRepository, times(1)).save(exam);
    }

    /**
     * When call prepareAndSave should return saved exam.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenPrepareAndSaveShouldReturnSavedExam() throws Exception {
        when(partOfSpeechService.getPartOfSpeechByName(partOfSpeech.getName())).thenReturn(partOfSpeech);

        assertThat(
                examService.prepareAndSave(
                        exam.getName(),
                        partOfSpeech.getName(),
                        0).getPartOfSpeech().getName(),
                is(exam.getPartOfSpeech().getName()));
        verify(partOfSpeechService, times(1)).getPartOfSpeechByName(partOfSpeech.getName());
    }

}
