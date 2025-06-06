package ikea.product.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "product_types")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;
}
