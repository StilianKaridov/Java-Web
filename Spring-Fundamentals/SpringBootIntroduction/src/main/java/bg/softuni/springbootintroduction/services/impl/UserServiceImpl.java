package bg.softuni.springbootintroduction.services.impl;

import bg.softuni.springbootintroduction.domain.dto.UserImportDTO;
import bg.softuni.springbootintroduction.domain.entity.User;
import bg.softuni.springbootintroduction.domain.entity.UserRole;
import bg.softuni.springbootintroduction.repositories.UserRepository;
import bg.softuni.springbootintroduction.repositories.UserRoleRepository;
import bg.softuni.springbootintroduction.services.UserService;
import bg.softuni.springbootintroduction.utils.enums.Role;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.mapper = mapper;
    }


    @Override
    public void seedUsers() {
        if (this.userRepository.count() == 0) {
            UserImportDTO user1 = new UserImportDTO("ivan0123", "ivancho", "Ivan", "Ivanov", true, Role.Admin, Instant.now());
            UserImportDTO user2 = new UserImportDTO("toshko0101", "todor1234", "Todor", "Todorov", false, Role.User, Instant.now());

            User toInsert = this.mapper.map(user1, User.class);
            User toInsert2 = this.mapper.map(user2, User.class);

            UserRole admin = this.userRoleRepository.findFirstByRole(user1.getRole());
            UserRole user = this.userRoleRepository.findFirstByRole(user2.getRole());

            toInsert.setRole(admin);
            toInsert2.setRole(user);

            this.userRepository.saveAndFlush(toInsert);
            this.userRepository.saveAndFlush(toInsert2);
        }
    }
}
