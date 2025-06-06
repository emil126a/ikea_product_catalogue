package ikea.product.demo.controller;

import ikea.product.demo.dto.input.ProductDTO;
import ikea.product.demo.entity.Product;
import ikea.product.demo.exception.ProductNotFoundException;
import ikea.product.demo.repository.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;


import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Product Management", description = "APIs for managing products")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/products")
    @Operation(summary = "Fetches all products", description = "It fetches all products by name type and color.")
    public Page<Product> getProducts(
            @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
            @PageableDefault(size = 10) final Pageable pageable
    ) {
        Page<Product> products = productRepository.findAll(pageable);
        return products;
    }


    @PostMapping("/products")
    public Product createProduct(@Valid @RequestBody ProductDTO productDTO) {

        Product product = new Product();
        product.setName("lwekl");
        return product;
    }

    @GetMapping("/products/{id}")
    public Product findProduct(@PathVariable Integer id) {
        return productRepository.findById(id)
                .orElseThrow(
                        () -> new ProductNotFoundException("Product not found: " + id)
                );
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
