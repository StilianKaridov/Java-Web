package bg.softuni.springbootintroduction.repositories;

import bg.softuni.springbootintroduction.domain.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    Brand getBrandByName(String name);

    List<Brand> findAll();
}
