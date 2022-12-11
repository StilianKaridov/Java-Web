package bg.softuni.springbootintroduction.services;

import bg.softuni.springbootintroduction.domain.binding.UserRegisterBindingModel;

public interface UserService {

    void seedUsers();

    boolean isUsernameFree(String username);

    void register(UserRegisterBindingModel userRegister);

    boolean authenticate(String username, String password);

    void loginUser(String username);
}
