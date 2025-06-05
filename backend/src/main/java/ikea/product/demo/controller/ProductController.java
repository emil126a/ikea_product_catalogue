package ikea.product.demo.controller;

import ikea.product.demo.dto.input.ProductDTO;

import ikea.product.demo.entity.Product;
import ikea.product.demo.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ProductController {

    private ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        List<Product> products =productRepository.findAll();
        return products;
    }

    @PostMapping("/products")
    public List<Product> createProduct(@RequestBody ProductDTO productDTO) {
        return List.of();
    }


    @GetMapping("/products/{id}")
    public Product findProduct(@PathVariable int id) {
        Product product = new Product();

        return product;
    }

    @PatchMapping("/products/{id}")
    public Product updateProduct(@PathVariable int id) {
        Product product = new Product();

        return product;
    }

    @DeleteMapping("/products/{id}")
    public String deleteEmployee() {
        return "";
    }
}
