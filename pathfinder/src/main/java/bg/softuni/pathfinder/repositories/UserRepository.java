package bg.softuni.pathfinder.repositories;

import bg.softuni.pathfinder.models.User;
import bg.softuni.pathfinder.models.binding.UserLoginBinding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select new bg.softuni.pathfinder.models.binding.UserLoginBinding" +
            "(u.username, u.password) from User u " +
            "where u.username like :username")
    Optional<UserLoginBinding> findFirstByUsername(String username);
}
