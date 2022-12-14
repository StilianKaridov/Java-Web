package bg.softuni.springbootintroduction.service.impl;

import bg.softuni.springbootintroduction.domain.entity.UserRole;
import bg.softuni.springbootintroduction.repository.UserRoleRepository;
import bg.softuni.springbootintroduction.service.UserRoleService;
import bg.softuni.springbootintroduction.utils.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void seedUserRoles() {
        if (this.userRoleRepository.count() == 0) {
            UserRole user = new UserRole(Role.USER);
            UserRole admin = new UserRole(Role.ADMIN);

            this.userRoleRepository.saveAndFlush(user);
            this.userRoleRepository.saveAndFlush(admin);
        }
    }
}
