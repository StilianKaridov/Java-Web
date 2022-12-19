package bg.softuni.springbootintroduction.repositories;

import bg.softuni.springbootintroduction.domain.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {

    Model findFirstByName(String name);
}
