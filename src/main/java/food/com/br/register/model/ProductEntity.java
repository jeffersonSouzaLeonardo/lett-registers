package food.com.br.register.model;

import food.com.br.register.dto.ProductDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "product")
@Entity(name = "Product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="id_category")
    private Long idCategory;

    private String description;

    private String status;

    public ProductEntity(ProductDto productDto){
        this.id = productDto.id();
        this.idCategory = productDto.idCategory();
        this.description = productDto.description();
        this.status = productDto.status();
    }

}
