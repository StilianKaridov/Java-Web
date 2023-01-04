package bg.softuni.springbootintroduction.repositories;

import bg.softuni.springbootintroduction.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsernameEquals(String username);
}
