package food.com.br.register.dto;

import food.com.br.register.model.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataProductDto(
        Long id,

        @NotNull
        Long idCategory,

        @NotBlank
        String description,

        @NotBlank
        String status
) {
        public DataProductDto(Product product){
                this(product.getId(), product.getIdCategory(), product.getDescription(), product.getStatus());
        }


}
