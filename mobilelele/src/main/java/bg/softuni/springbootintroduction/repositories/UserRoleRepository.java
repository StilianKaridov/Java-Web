package bg.softuni.springbootintroduction.repositories;

import bg.softuni.springbootintroduction.domain.entity.UserRole;
import bg.softuni.springbootintroduction.utils.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    UserRole findFirstByRole(Role role);
}
