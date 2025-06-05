package ikea.product.demo.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.ManyToAny;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addColour(Colour colour) {

        if (colours == null) {
            colours = new ArrayList<>();
        }

        colours.add(colour);
    }

    public int getId() {
        return id;
    }

    public List<Colour> getColours() {
        return colours;
    }
}
