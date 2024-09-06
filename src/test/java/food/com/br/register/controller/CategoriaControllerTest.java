package food.com.br.register.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import food.com.br.register.dto.CategoryDto;
import food.com.br.register.model.CategoryEntity;
import food.com.br.register.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CategoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        categoryRepository.deleteAll();
    }

    @Test
    void shouldSaveCategoryWithSuccess() throws Exception {
        var categoryDto = new CategoryDto(null, "Lanches");
        var map = new ObjectMapper();

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(map.writeValueAsString(categoryDto));

        var response = mockMvc.perform(builder).andReturn().getResponse();
        var jsonProp = JsonPath.compile("$.description").read(response.getContentAsString());
        assertThat(jsonProp).isEqualTo("Lanches");

        var categories = categoryRepository.findAll();
        assertThat(categories.get(0).getDescription()).isEqualTo("Lanches");
    }

    @Test
    void shouldGetCategory() throws Exception {
        var category = new CategoryEntity();
        category.setDescription("Lanches");
        category = categoryRepository.saveAndFlush(category);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/category/{id}", category.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        var response = mockMvc.perform(builder).andExpect(status().isOk()).andReturn().getResponse();
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(category.getId());
        assertThat(categoryEntity).isNotNull();
    }

    @Test
    void shouldEditCategoryWithSuccess() throws Exception {
        var category = new CategoryEntity();
        category.setDescription("Lanches");
        category = categoryRepository.saveAndFlush(category);
        category.setDescription("Bebidas");

        var map = new ObjectMapper();
        var dto = map.convertValue(category, CategoryDto.class);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(map.writeValueAsString(dto));

        var response = mockMvc.perform(builder).andReturn().getResponse();
        var jsonProp = JsonPath.compile("$.description").read(response.getContentAsString());
        assertThat(jsonProp).isEqualTo("Bebidas");
    }

    @Test
    void shouldDeleteCategoryWithSuccess() throws Exception {
        var category = new CategoryEntity();
        category.setDescription("Lanches");
        category = categoryRepository.saveAndFlush(category);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/category/{id}", category.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder).andExpect(status().isNoContent());

        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(category.getId());
        assertThat(categoryEntity).isEmpty();
    }
}