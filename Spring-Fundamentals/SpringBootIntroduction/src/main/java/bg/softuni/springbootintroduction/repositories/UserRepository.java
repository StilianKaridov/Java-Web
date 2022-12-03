package bg.softuni.springbootintroduction.repositories;

import bg.softuni.springbootintroduction.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
