package com.voronin.sentence.controller;

import com.voronin.sentence.domain.Category;
import com.voronin.sentence.service.CategoryService;
import com.voronin.sentence.service.SentenceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * CategoryController test class.
 *
 * @author Alexey Voronin.
 * @since 19.08.2019.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = CategoryController.class, secure = false)
@AutoConfigureMockMvc
@WithMockUser
public class CategoryControllerTest {

    /**
     * Main entry point for server-side Spring MVC test support.
     *
     * @see MockMvc
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Mock CategoryService.
     */
    @MockBean
    private CategoryService categoryService;

    /**
     * Mock SentenceService.
     */
    @MockBean
    private SentenceService sentenceService;

    /**
     * Mock Category class.
     */
    @MockBean
    private Category category;

    /**
     * When mapping /category/categories then return status isOk and call
     * method getCategories once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingCategoriesShouldReturnStatusOkAndCallMethodGetCategoriesOnce() throws Exception {
        this.mockMvc.perform(get("/category/categories")).andExpect(status().isOk());

        verify(this.categoryService, times(1)).getCategories();
    }
}
