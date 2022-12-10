package bg.softuni.pathfinder.services.impl;

import bg.softuni.pathfinder.models.binding.UserLoginBinding;
import bg.softuni.pathfinder.repositories.UserRepository;
import bg.softuni.pathfinder.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserLoginBinding findUserByUsername(String username) {
        Optional<UserLoginBinding> user = this.userRepository.findFirstByUsername(username);

        return user.orElse(null);
    }
}
