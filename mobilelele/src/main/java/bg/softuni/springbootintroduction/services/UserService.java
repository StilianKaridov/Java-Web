package bg.softuni.springbootintroduction.services;

import bg.softuni.springbootintroduction.domain.binding.UserLoginBindingModel;
import bg.softuni.springbootintroduction.domain.binding.UserRegisterBindingModel;
import bg.softuni.springbootintroduction.domain.entity.User;

import java.util.Optional;

public interface UserService {

    void seedUsers();

    Optional<User> findByUsername(String username);

    void register(UserRegisterBindingModel userRegister);

    boolean authenticate(UserLoginBindingModel user);

    void loginUser(String username);

    void logout();

    boolean isCurrentUserAdmin();
}
