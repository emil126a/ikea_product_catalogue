package ikea.product.demo.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST
            })
    @JoinTable(
            name = "product_colour",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "colour_id")
    )
    private List<Colour> colours;

    @ManyToOne
    @JoinColumn(name = "product_type_id", nullable = false)
    private ProductType productType;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public void addColour(Colour colour) {
        if (colours == null) {
            colours = new ArrayList<>();
        }

        colours.add(colour);
    }
}
