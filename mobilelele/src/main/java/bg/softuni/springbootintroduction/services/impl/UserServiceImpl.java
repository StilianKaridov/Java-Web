package bg.softuni.springbootintroduction.services.impl;

import bg.softuni.springbootintroduction.domain.binding.UserLoginBindingModel;
import bg.softuni.springbootintroduction.domain.binding.UserRegisterBindingModel;
import bg.softuni.springbootintroduction.domain.dto.UserImportDTO;
import bg.softuni.springbootintroduction.domain.entity.User;
import bg.softuni.springbootintroduction.domain.entity.UserRole;
import bg.softuni.springbootintroduction.repositories.UserRepository;
import bg.softuni.springbootintroduction.repositories.UserRoleRepository;
import bg.softuni.springbootintroduction.security.CurrentUser;
import bg.softuni.springbootintroduction.services.UserService;
import bg.softuni.springbootintroduction.utils.enums.Role;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final CurrentUser currentUser;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, ModelMapper mapper, PasswordEncoder passwordEncoder, CurrentUser currentUser) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.currentUser = currentUser;
    }

    @Override
    public void seedUsers() {
        if (this.userRepository.count() == 0) {
            UserImportDTO user1 = new UserImportDTO("ivan0123", passwordEncoder.encode("ivancho"), "Ivan", "Ivanov", true, Role.Admin, Instant.now());
            UserImportDTO user2 = new UserImportDTO("toshko0101", passwordEncoder.encode("todor1234"), "Todor", "Todorov", false, Role.User, Instant.now());

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

    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findFirstByUsernameIgnoreCase(username);
    }

    @Override
    public void register(UserRegisterBindingModel userRegister) {
        String firstNameTrimmed = userRegister.getFirstName().trim();
        String lastNameTrimmed = userRegister.getLastName().trim();
        String usernameTrimmed = userRegister.getUsername().trim();

        userRegister.setFirstName(firstNameTrimmed);
        userRegister.setLastName(lastNameTrimmed);
        userRegister.setUsername(usernameTrimmed);

        User user = this.mapper.map(userRegister, User.class);

        user.setRole(setUserRole());

        user.setPassword(passwordEncoder.encode(userRegister.getPassword()));
        user.setCreated(Instant.now());
        user.setActive(true);

        this.userRepository.saveAndFlush(user);
    }

    @Override
    public boolean authenticate(UserLoginBindingModel user) {
        Optional<User> optUser = findByUsername(user.getUsername());

        if (optUser.isEmpty()) {
            return false;
        }

        return passwordEncoder.matches(user.getPassword(), optUser.get().getPassword());
    }

    @Override
    public void loginUser(String username) {
        this.currentUser.setAnonymous(false);
        this.currentUser.setUsername(username);
    }

    @Override
    public void logout() {
        this.currentUser.setAnonymous(true);
    }

    @Override
    public boolean isCurrentUserAdmin() {
        Optional<User> user = findByUsername(this.currentUser.getUsername());

        if (user.isEmpty()) {
            return false;
        }

        return user.get().getRole().getRole().name().equals("Admin");
    }

    private UserRole setUserRole() {
        if (this.userRepository.count() == 0) {
            return this.userRoleRepository.findFirstByRole(Role.Admin);
        }

        return this.userRoleRepository.findFirstByRole(Role.User);
    }
}
