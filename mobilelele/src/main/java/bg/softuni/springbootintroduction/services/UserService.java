package bg.softuni.springbootintroduction.services;

import bg.softuni.springbootintroduction.domain.binding.UserRegisterBindingModel;

public interface UserService {

    void seedUsers();

    void register(UserRegisterBindingModel userRegister);
}
