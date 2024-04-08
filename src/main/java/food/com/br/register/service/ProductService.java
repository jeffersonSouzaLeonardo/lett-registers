package food.com.br.register.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import food.com.br.register.dto.ProductDto;
import food.com.br.register.model.ProductEntity;
import food.com.br.register.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Slf4j
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public ProductEntity save(ProductDto productDto){
        var product = new ProductEntity(productDto);
        return productRepository.saveAndFlush(product);
    }

    public Page<ProductEntity> findAll(@PageableDefault(size = 10, sort = {"description"}) Pageable pageable){
        return productRepository.findAll(pageable);
    }

    public Optional<ProductEntity> findId(Long id){
        return productRepository.findById(id);
    }

    public void delete(Long id){
        productRepository.deleteById(id);
    }

    public ProductEntity edit(ProductDto productDto) throws JsonMappingException {
        ObjectMapper objectMapper = new ObjectMapper();
        var product = productRepository.findById(productDto.id()).get();
        if (product != null){
            product = objectMapper.updateValue(product, productDto);
            productRepository.saveAndFlush(product);
        }else {
            log.info("Produto não encontrado para Edição com o ID: " + productDto.id() );
        }

        return product;
    }

}
