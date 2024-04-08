package food.com.br.register.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import food.com.br.register.dto.ProductDto;
import food.com.br.register.model.ProductEntity;
import food.com.br.register.repository.ProductRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProductRepository productRepository;

    @BeforeEach
     private void before(){
        productRepository.deleteAll();
    }

    @Test
    void shouldReturnAllProducts() throws Exception {
        saveProducts(getProducts());
        var response = mockMvc.perform(get("/product"))
                .andReturn().getResponse();

        JsonPath path = JsonPath.compile("$.content");
        List<ProductDto> dtoProducts = path.read(response.getContentAsString());

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    void shouldEditProductWithSucess() throws Exception {
        productRepository.deleteAll();
        ObjectMapper map = new ObjectMapper();

        var product = new ProductEntity();
        product.setIdCategory(1L);
        product.setDescription("X-BURGUER");
        product.setStatus("ATIVO");
        product = productRepository.saveAndFlush(product);
        product.setDescription("Produto Alterado");

        var dto = map.convertValue(product, ProductDto.class);

        var json = JsonPath.parse(dto).json();

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.put("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(map.writeValueAsString(dto) );

        var response = mockMvc.perform(builder).andReturn().getResponse();
        var prorep = JsonPath.compile("$.description").read(response.getContentAsString());
        assertThat(prorep).isEqualTo("Produto Alterado");
    }

    @Test
    void shouldSaveProductWithSuccess() throws Exception {
        productRepository.deleteAll();
        var productDto = new ProductDto(null, 1L, "X-BURGUER", "ATIVO");
        var map = new ObjectMapper();
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/product")
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .accept(MediaType.APPLICATION_JSON)
                                                        .characterEncoding("UTF-8")
                                                        .content(map.writeValueAsString(productDto) );

       var response = mockMvc.perform(builder).andReturn().getResponse();
       var jsonProp = JsonPath.compile("$.description").read(response.getContentAsString());
       assertThat(jsonProp).isEqualTo("X-BURGUER");

       var products = productRepository.findAll();
       assertThat(products.get(0).getDescription()).isEqualTo("X-BURGUER");
    }

    @Test
    void shouldDeleteProductWithSucess() throws Exception {
        productRepository.deleteAll();
        ObjectMapper map = new ObjectMapper();

        var product = new ProductEntity();
        product.setIdCategory(1L);
        product.setDescription("X-BURGUER");
        product.setStatus("ATIVO");
        product = productRepository.saveAndFlush(product);

        var productFind = productRepository.findById(product.getId()).get();

        assertThat(productFind).isNotNull();

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.delete("/product/{id}", productFind.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON);
        var response = mockMvc.perform(builder).andExpect(status().is(204)).andReturn().getResponse();
        Optional<ProductEntity> prod = productRepository.findById(product.getId());
        assertThat(prod).isEmpty();
    }

    @Test
    void shouldGetProduc() throws Exception {
        productRepository.deleteAll();
        ObjectMapper map = new ObjectMapper();

        var product = new ProductEntity();
        product.setIdCategory(1L);
        product.setDescription("X-BURGUER");
        product.setStatus("ATIVO");
        product = productRepository.saveAndFlush(product);

        var productFind = productRepository.findById(product.getId()).get();

        assertThat(productFind).isNotNull();

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get("/product/{id}", productFind.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON);
        var response = mockMvc.perform(builder).andExpect(status().isOk()).andReturn().getResponse();
        Optional<ProductEntity> prod = productRepository.findById(productFind.getId());
        assertThat(prod).isNotNull();
    }


    private void saveProducts(List<ProductEntity> productEntities){
        productRepository.saveAll(productEntities);
    }

    private List<ProductEntity> getProducts(){
        List<ProductEntity> productEntities = new ArrayList<>();
        productEntities.add(new ProductEntity(new Random().nextLong(), 1L, "x-burguer", "ATIVO"));
        productEntities.add(new ProductEntity(new Random().nextLong(), 1L, "x-salada", "ATIVO"));
        productEntities.add(new ProductEntity(new Random().nextLong(), 1L, "x-bacon", "ATIVO"));
        productEntities.add(new ProductEntity(new Random().nextLong(), 1L, "x-tudo", "ATIVO"));
        productEntities.add(new ProductEntity(new Random().nextLong(), 2L, "coca-cola", "ATIVO"));
        productEntities.add(new ProductEntity(new Random().nextLong(), 2L, "coca-cola zero", "ATIVO"));
        productEntities.add(new ProductEntity(new Random().nextLong(), 2L, "agua", "ATIVO"));
        productEntities.add(new ProductEntity(new Random().nextLong(), 2L, "agua com gás", "ATIVO"));
        return productEntities;
    }

}