package food.com.br.register.service;

import food.com.br.register.dto.CategoryDto;
import food.com.br.register.exceptions.EntityNotFoundException;
import food.com.br.register.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import food.com.br.register.model.CategoryEntity;
import food.com.br.register.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Service
@Slf4j
public class CategoryService {
    
    @Autowired
    CategoryRepository categoryRepository;

    public CategoryEntity save(CategoryDto categoryDto) {
        var category = new CategoryEntity(categoryDto);
        return  categoryRepository.save(category);
    }

    public Page<CategoryEntity> findAll(@PageableDefault(size = 10, sort = {"description"}) Pageable pageable) {
        return  categoryRepository.findAll(pageable);
    }

    public Optional<CategoryEntity> findById(Long id) {
        return  categoryRepository.findById(id);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    public Object edit(CategoryDto categoryDto) {
        var category = categoryRepository.findById(categoryDto.id())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com o ID: " + categoryDto.id()));

        category.setDescription(categoryDto.description());

        categoryRepository.saveAndFlush(category);

        return category;
    }
}
