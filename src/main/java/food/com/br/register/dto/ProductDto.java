package food.com.br.register.dto;

import food.com.br.register.model.ProductEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductDto(
        Long id,

        @NotNull
        Long idCategory,

        @NotBlank
        String description,

        @NotBlank
        String status
) {
        public ProductDto(ProductEntity productEntity){
                this(productEntity.getId(), productEntity.getIdCategory(), productEntity.getDescription(), productEntity.getStatus());
        }


}
