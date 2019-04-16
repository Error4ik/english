package com.voronin.english.service;

import com.voronin.english.domain.Category;
import com.voronin.english.domain.Exam;
import com.voronin.english.repository.ExamRepository;
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
 * ExamService test class.
 *
 * @author Alexey Voronin.
 * @since 19.12.2018.
 */
public class ExamServiceTest {

    /**
     * Mock ExamRepository.
     */
    private ExamRepository examRepository = mock(ExamRepository.class);

    /**
     * Mock CategoryService.
     */
    private CategoryService categoryService = mock(CategoryService.class);

    /**
     * Class for test.
     */
    private Exam exam = new Exam();

    /**
     * Class for test.
     */
    private Category category = new Category();

    /**
     * The class object under test.
     */
    private ExamService examService = new ExamService(examRepository, categoryService);

    /**
     * UUID id for test.
     */
    private UUID uuid = UUID.randomUUID();

    /**
     * initialization of objects for the tests.
     */
    @Before
    public void init() {
        category.setId(uuid);
        category.setName("category");
        exam.setId(uuid);
        exam.setName("exam");
        exam.setCategory(category);
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
        when(examRepository.getExamByCategory(category)).thenReturn(exam);

        assertThat(examService.getExamByCategory(category), is(exam));
        verify(examRepository, times(1)).getExamByCategory(category);
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
        when(categoryService.getCategoryByName(category.getName())).thenReturn(category);

        assertThat(
                examService.prepareAndSave(
                        exam.getName(),
                        category.getName(),
                        0).getCategory().getName(),
                is(exam.getCategory().getName()));
        verify(categoryService, times(1)).getCategoryByName(category.getName());
    }
}
