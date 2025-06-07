package ikea.product.demo.repository;

import ikea.product.demo.entity.ProductType;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {
    ProductType findOneById(Integer productTypeId);
}
