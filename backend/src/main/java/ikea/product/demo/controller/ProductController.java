package ikea.product.demo.controller;

import ikea.product.demo.dto.input.ProductDTO;
import ikea.product.demo.entity.Product;
import ikea.product.demo.exception.ProductNotFoundException;
import ikea.product.demo.repository.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Product Management", description = "APIs for managing products")
public class ProductController {

    private ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/products")
    @Operation(summary = "Fetches all products", description = "It fetches all products by name type and color.")
    public List<Product> getProducts() {
        List<Product> products = productRepository.findAll();
        return products;
    }

    @PostMapping("/products")
    public List<Product> createProduct(@RequestBody ProductDTO productDTO) {
        return List.of();
    }

    @GetMapping("/products/{id}")
    public Product findProduct(@PathVariable int id) {
        Product product = productRepository.findById(id);

        if (product == null) {
            throw new ProductNotFoundException("Product not found: " + id);
        }

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
