package food.com.br.register.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import food.com.br.register.dto.CategoryDto;
import food.com.br.register.dto.ProductDto;
import food.com.br.register.model.CategoryEntity;
import food.com.br.register.service.CategoryService;
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
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping
    @Transactional
    public ResponseEntity post(@RequestBody @Valid CategoryDto categoryDto, UriComponentsBuilder uriComponentsBuilder){
        var uri = uriComponentsBuilder.path("/category/{id}").buildAndExpand(categoryDto.id()).toUri();
        var product = categoryService.save(categoryDto);
        return ResponseEntity.created(uri).body(new CategoryDto(product));
    }

    @GetMapping()
    public ResponseEntity<Page<CategoryDto>> get(Pageable pageable) {
        return ResponseEntity.ok(categoryService.findAll(pageable).map(CategoryDto::new));
    }

    @GetMapping(path ="/{id}")
    public ResponseEntity get(@PathVariable Long id) {
        var product = categoryService.findById(id);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping(path ="/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity edit(@RequestBody @Valid CategoryDto categoryDto, UriComponentsBuilder uriComponentsBuilder) throws JsonMappingException {
        var uri = uriComponentsBuilder.path("/category").buildAndExpand(categoryDto.id()).toUri();
        var category = categoryService.edit(categoryDto);
        return ResponseEntity.created(uri).body(new CategoryDto((CategoryEntity) category));
    }

}
