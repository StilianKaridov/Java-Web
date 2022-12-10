package bg.softuni.pathfinder.services;

import bg.softuni.pathfinder.models.binding.UserLoginBinding;

public interface UserService {

    UserLoginBinding findUserByUsername(String username);
}
