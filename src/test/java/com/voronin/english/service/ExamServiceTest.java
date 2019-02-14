package com.voronin.english.service;

import com.voronin.english.domain.Category;
import com.voronin.english.domain.Exam;
import com.voronin.english.repository.ExamRepository;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * ExamService test class.
 *
 * @author Alexey Voronin.
 * @since 19.12.2018.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ExamService.class)
@WithMockUser(username = "user", roles = {"USER"})
public class ExamServiceTest {

    /**
     * The class object under test.
     */
    @Autowired
    private ExamService examService;

    /**
     * Mock JavaMailSender.
     */
    @MockBean
    private JavaMailSender javaMailSender;

    /**
     * Mock ExamRepository.
     */
    @MockBean
    private ExamRepository examRepository;

    /**
     * Mock CategoryService.
     */
    @MockBean
    private CategoryService categoryService;

    /**
     * Class for test.
     */
    private Exam exam = new Exam();

    /**
     * Class for test.
     */
    private Category category = new Category();

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
    }

    /**
     * When call prepareAndSave should return saved exam.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenPrepareAndSaveShouldReturnSavedExam() throws Exception {
        when(categoryService.getCategoryByName(category.getName())).thenReturn(category);
        whenNew(Exam.class).withArguments(exam.getName(), category).thenReturn(exam);
        when(examRepository.save(any(Exam.class))).thenReturn(exam);

        assertThat(examService.prepareAndSave(exam.getName(), category.getName(), 0), is(exam));
    }
}
