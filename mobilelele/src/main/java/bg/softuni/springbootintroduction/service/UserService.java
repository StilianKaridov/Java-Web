package bg.softuni.springbootintroduction.service;

import bg.softuni.springbootintroduction.domain.binding.UserRegisterBindingModel;

public interface UserService {

    void seedUsers();

    void register(UserRegisterBindingModel userRegister);
}
