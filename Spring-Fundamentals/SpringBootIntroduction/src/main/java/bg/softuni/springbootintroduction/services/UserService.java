package bg.softuni.springbootintroduction.services;

import bg.softuni.springbootintroduction.domain.binding.UserLoginBindingModel;
import bg.softuni.springbootintroduction.domain.binding.UserRegisterBindingModel;

public interface UserService {

    void seedUsers();

    boolean isUsernameFree(String username);

    void register(UserRegisterBindingModel userRegister);

    boolean authenticateUser(UserLoginBindingModel userLogin);

    void login(UserLoginBindingModel userLogin);
}
