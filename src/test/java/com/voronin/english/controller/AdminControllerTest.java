package com.voronin.english.controller;

import com.voronin.english.domain.CardFilled;
import com.voronin.english.domain.Category;
import com.voronin.english.service.WordService;
import com.voronin.english.service.CategoryService;
import com.voronin.english.service.NounService;
import com.voronin.english.service.QuestionService;
import com.voronin.english.service.ExamService;
import com.voronin.english.service.PhraseForTrainingService;
import com.voronin.english.service.UserService;
import com.voronin.english.service.RoleService;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * AdminController test.
 *
 * @author Alexey Voronin.
 * @since 29.11.2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminController.class)
@AutoConfigureMockMvc
@WithMockUser(authorities = "ADMIN")
public class AdminControllerTest {

    /**
     * Main entry point for server-side Spring MVC test support.
     *
     * @see MockMvc
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Mock WordService.
     */
    @MockBean
    private WordService wordService;

    /**
     * Mock CategoryService.
     */
    @MockBean
    private CategoryService categoryService;

    /**
     * Mock CardFilled class.
     */
    @MockBean
    private CardFilled cardFilled;

    /**
     * Mock Category class.
     */
    @MockBean
    private Category category;

    /**
     * Mock ExamService.
     */
    @MockBean
    private ExamService examService;

    /**
     * Mock QuestionService.
     */
    @MockBean
    private QuestionService questionService;

    /**
     * Mock PhraseForTrainingService.
     */
    @MockBean
    private PhraseForTrainingService phraseForTrainingService;

    /**
     * Mock UserService.
     */
    @MockBean
    private UserService userService;

    /**
     * Mock RoleService.
     */
    @MockBean
    private RoleService roleService;

    /**
     * Mock NounService.
     */
    @MockBean
    private NounService nounService;

    /**
     * When mapping '/admin/add-noun' should call the prepareAndSave method of the NounService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetMappingAddNounShouldReturnStatusOkAndOneCallPrepareAndSaveMethod() throws Exception {
        this.mockMvc
                .perform(get("/admin/add-noun").flashAttr("cardFilled", cardFilled))
                .andExpect(status().isOk());

        verify(this.nounService, times(1)).prepareAndSave(cardFilled, null);
    }

    /**
     * When mapping '/admin/add-word' should call the prepareAndSave method of the WordService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetMappingAddWordShouldReturnStatusOkAndOneCallPrepareAndSaveMethod() throws Exception {
        this.mockMvc
                .perform(get("/admin/add-word").flashAttr("cardFilled", cardFilled))
                .andExpect(status().isOk());

        verify(this.wordService, times(1)).prepareAndSave(cardFilled);
    }

    /**
     * When mapping '/admin/add-category' should call the prepareAndSave method of the CategoryService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetMappingAddCategoryShouldReturnStatusOkAndOneCallPrepareAndSaveMethod() throws Exception {
        this.mockMvc
                .perform(get("/admin/add-category").flashAttr("category", category))
                .andExpect(status().isOk());

        verify(this.categoryService, times(1)).prepareAndSave(category, null);
    }

    /**
     * When mapping '/admin/add-question' should call the prepareAndSave method of the QuestionService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetMappingAddQuestionShouldReturnStatusOkAndCallPrepareAndSaveMethod() throws Exception {
        String exam = "exam";
        String word = "word";
        List<String> list = Lists.newArrayList("word", "word", "word");
        this.mockMvc.perform(get("/admin/add-question")
                .param("exam", exam)
                .param("noun", word)
                .param("variants", "word")
                .param("variants", "word")
                .param("variants", "word"))
                .andExpect(status().isOk());

        verify(this.questionService, times(1)).prepareAndSave(exam, word, list);
    }

    /**
     * When mapping '/admin/add-exam' should call the prepareAndSave method of the ExamService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingAddExamShouldReturnStatusOkAndCallPrepareAndSaveMethod() throws Exception {
        String name = "name";
        String categoryName = "category";
        int examType = 0;
        this.mockMvc.perform(get("/admin/add-exam")
                .param("name", name)
                .param("category", categoryName)
                .param("type", String.valueOf(examType)))
                .andExpect(status().isOk());

        verify(this.examService, times(1)).prepareAndSave(name, categoryName, examType);
    }

    /**
     * When mapping '/admin/add-phrase-for-training' should call
     * the prepareAndSave method of the PhraseForTrainingService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingAddPhraseFromTrainingShouldReturnStatusOkAndCallPrepareAndSaveMethodOneTime()
            throws Exception {
        String phrase = "phrase";
        String translate = "translate";
        String category = "category";
        this.mockMvc.perform(get("/admin/add-phrase-for-training")
                .param("phrase", phrase)
                .param("translate", translate)
                .param("category", category))
                .andExpect(status().isOk());
        verify(this.phraseForTrainingService, times(1))
                .prepareAndSave(phrase, translate, category);
    }

//    /**
//     * When mapping '/users' should call the getUsers method of the UserService class once.
//     *
//     * @throws Exception exception.
//     */
//    @Test
//    public void whenMappingUsersShouldReturnStatusOkAndCallGetUsersMethodOneTime() throws Exception {
//        this.mockMvc.perform(get("/admin/users")).andExpect(status().isOk());
//
//        verify(this.userService, times(1)).getUsers();
//    }
//
//    /**
//     * When mapping '/roles' should call the getRoles method of the RoleService class once.
//     *
//     * @throws Exception exception.
//     */
//    @Test
//    public void whenMappingRolesShouldReturnStatusOkAndCallGetRolesMethodOneTime() throws Exception {
//        this.mockMvc.perform(get("/admin/roles")).andExpect(status().isOk());
//
//        verify(this.roleService, times(1)).getRoles();
//    }

    /**
     * When mapping '/change-role' should call the getUsers method of the UserService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingChangeRoleShouldReturnStatusOkAndCallChangeUserRoleMethodOneTime() throws Exception {
        UUID uuid = UUID.randomUUID();
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        this.mockMvc.perform(get("/admin/change-role")
                .param("userId", uuid.toString())
                .param("roleId", uuid.toString()))
                .andExpect(status().isOk());

        verify(this.userService, times(1)).changeUserRole(principal, uuid, uuid);
    }
}
