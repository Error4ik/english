package com.voronin.sentence.service;

import com.voronin.sentence.domain.Exam;
import com.voronin.sentence.repository.ExamRepository;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * ExamService test.
 *
 * @author Alexey Voronin.
 * @since 11.10.2019.
 */
public class ExamServiceTest {

    /**
     * ExamRepository mock.
     */
    private final ExamRepository examRepository = mock(ExamRepository.class);

    /**
     * The class object under test.
     */
    private ExamService examService = new ExamService(examRepository);

    /**
     * Id for test.
     */
    private final UUID id = UUID.randomUUID();

    /**
     * Exam for test.
     */
    private final Exam exam = new Exam("exam", Exam.ExamType.FUTURE);

    /**
     * When call method getExamById should return Exam.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetExamByIdShouldReturnExam() throws Exception {
        when(examRepository.getExamById(id)).thenReturn(exam);

        assertThat(this.examService.getExamById(id), is(exam));
    }

    /**
     * When call method getExams should return list of Exams.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetExamsShouldReturnListOfExams() throws Exception {
        final List<Exam> examList = Lists.newArrayList(exam);

        when(this.examRepository.findAll()).thenReturn(examList);

        assertThat(this.examService.getExams(), is(examList));
    }

    /**
     * When call method save should return saved Exam.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenSaveShouldReturnSavedExam() throws Exception {
        final String examName = "exam";
        final String examType = "PAST";
        exam.setExamType(Exam.ExamType.PAST);

        when(this.examRepository.save(exam)).thenReturn(exam);

        assertThat(this.examService.save(examName, examType).getExamType(), is(exam.getExamType()));
    }
}
