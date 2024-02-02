package food.com.br.register.service;

import food.com.br.register.dto.DataProductDto;
import food.com.br.register.model.Product;
import food.com.br.register.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public Product save(DataProductDto dataProductDto){
        var product = new Product(dataProductDto);
        return productRepository.saveAndFlush(product);
    }

    public Page<Product> findAll(@PageableDefault(size = 10, sort = {"description"}) Pageable pageable){
        return productRepository.findAll(pageable);
    }

    public Optional<Product> findId(Long id){
        return productRepository.findById(id);
    }

    public void delete(Long id){
        productRepository.deleteById(id);
    }

    public Product edit(DataProductDto dataProductDto){
        var product = productRepository.findById(dataProductDto.id()).get();
        product.setIdCategory(dataProductDto.idCategory());
        product.setDescription(dataProductDto.description());
        product.setStatus(dataProductDto.status());
        productRepository.saveAndFlush(product);
        return product;
    }

}
