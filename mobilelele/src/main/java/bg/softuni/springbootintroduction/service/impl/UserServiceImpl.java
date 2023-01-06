package bg.softuni.springbootintroduction.service.impl;

import bg.softuni.springbootintroduction.domain.binding.UserRegisterBindingModel;
import bg.softuni.springbootintroduction.domain.dto.UserImportDTO;
import bg.softuni.springbootintroduction.domain.entity.User;
import bg.softuni.springbootintroduction.domain.entity.UserRole;
import bg.softuni.springbootintroduction.repository.UserRepository;
import bg.softuni.springbootintroduction.repository.UserRoleRepository;
import bg.softuni.springbootintroduction.service.UserService;
import bg.softuni.springbootintroduction.utils.enums.Role;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, ModelMapper mapper, PasswordEncoder passwordEncoder, UserDetailsServiceImpl userDetailsService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void seedUsers() {
        if (this.userRepository.count() == 0) {
            UserImportDTO user1 = new UserImportDTO("ivan0123", passwordEncoder.encode("ivancho"), "Ivan", "Ivanov", true, Role.ADMIN, Instant.now());
            UserImportDTO user2 = new UserImportDTO("toshko0101", passwordEncoder.encode("todor1234"), "Todor", "Todorov", false, Role.USER, Instant.now());

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

        User newUser = this.userRepository.save(user);

        UserDetails principal = userDetailsService.loadUserByUsername(newUser.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,
                newUser.getPassword(),
                principal.getAuthorities()
        );

        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);
    }

    private UserRole setUserRole() {
        if (this.userRepository.count() == 0) {
            return this.userRoleRepository.findFirstByRole(Role.ADMIN);
        }

        return this.userRoleRepository.findFirstByRole(Role.USER);
    }
}
