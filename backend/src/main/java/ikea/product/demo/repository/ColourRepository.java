package ikea.product.demo.repository;

import ikea.product.demo.entity.Colour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColourRepository extends JpaRepository<Colour, Integer> {

    Colour findOneById(int id);
}
