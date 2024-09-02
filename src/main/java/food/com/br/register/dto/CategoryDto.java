package food.com.br.register.dto;

import food.com.br.register.model.CategoryEntity;
import food.com.br.register.model.ProductEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryDto (

    Long id,

    @NotBlank
    String description
) {
    public  CategoryDto(CategoryEntity categoryEntity) {
        this(categoryEntity.getId(), categoryEntity.getDescription());
    }


}
