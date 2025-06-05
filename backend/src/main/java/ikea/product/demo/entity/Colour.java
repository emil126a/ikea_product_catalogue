package ikea.product.demo.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.Type;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "colours")

public class Colour {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "colours")
    private List<Product> products ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void addProduct(Product product) {

        if (products == null) {
            products = new ArrayList<>();
        }

        products.add(product);
    }
}
