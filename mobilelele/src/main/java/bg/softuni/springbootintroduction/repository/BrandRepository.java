package bg.softuni.springbootintroduction.repository;

import bg.softuni.springbootintroduction.domain.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    Brand getBrandByName(String name);
}
