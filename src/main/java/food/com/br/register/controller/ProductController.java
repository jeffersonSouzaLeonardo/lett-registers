package food.com.br.register.controller;

import food.com.br.register.dto.DataProductDto;
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
    public ResponseEntity post(@RequestBody @Valid DataProductDto dataProductDto, UriComponentsBuilder uriComponentsBuilder){

        var uri = uriComponentsBuilder.path("/product/{id}").buildAndExpand(dataProductDto.id()).toUri();
        var product = productService.save(dataProductDto);
        return ResponseEntity.created(uri).body(new DataProductDto(product));
    }

    @GetMapping()
    public ResponseEntity<Page<DataProductDto>> get(Pageable pageable) {
        var dataProductDtos = productService.findAll(pageable).map(DataProductDto::new);
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
    public ResponseEntity edit(@RequestBody @Valid DataProductDto dataProductDto, UriComponentsBuilder uriComponentsBuilder) {
        var uri = uriComponentsBuilder.path("/product/{id}").buildAndExpand(dataProductDto.id()).toUri();
        var product = productService.edit(dataProductDto);
        return ResponseEntity.created(uri).body(new DataProductDto(product));
    }

}
