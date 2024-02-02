package food.com.br.register.model;

import food.com.br.register.dto.DataProductDto;
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
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="id_category")
    private Long idCategory;

    private String description;

    private String status;

    public Product(DataProductDto dataProductDto){
        this.id = dataProductDto.id();
        this.idCategory = dataProductDto.idCategory();
        this.description = dataProductDto.description();
        this.status = dataProductDto.status();
    }

}
