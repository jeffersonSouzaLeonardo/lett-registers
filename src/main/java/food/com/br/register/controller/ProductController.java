package food.com.br.register.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import food.com.br.register.dto.ProductDto;
import food.com.br.register.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping
    @Transactional
    public ResponseEntity post(@RequestBody @Valid ProductDto productDto, UriComponentsBuilder uriComponentsBuilder){
        var uri = uriComponentsBuilder.path("/product/{id}").buildAndExpand(productDto.id()).toUri();
        var product = productService.save(productDto);
        return ResponseEntity.created(uri).body(new ProductDto(product));
    }

    @GetMapping()
    public ResponseEntity<Page<ProductDto>> get(Pageable pageable) {
        var dataProductDtos = productService.findAll(pageable).map(ProductDto::new);
        return ResponseEntity.ok(dataProductDtos);
    }

    @GetMapping(path ="/{id}")
    public ResponseEntity get(@PathVariable Long id) {
        var product = productService.findId(id);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping(path ="/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity edit(@RequestBody @Valid ProductDto productDto, UriComponentsBuilder uriComponentsBuilder) throws JsonMappingException {
        var uri = uriComponentsBuilder.path("/product").buildAndExpand(productDto.id()).toUri();
        var product = productService.edit(productDto);
        return ResponseEntity.created(uri).body(new ProductDto(product));
    }

}
