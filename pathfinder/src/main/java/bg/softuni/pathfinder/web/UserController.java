package bg.softuni.pathfinder.web;

import bg.softuni.pathfinder.models.binding.UserLoginBinding;
import bg.softuni.pathfinder.models.binding.UserRegisterBinding;
import bg.softuni.pathfinder.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(UserRegisterBinding userRegister) {

        if (this.userService.findUserByUsername(userRegister.getUsername()) != null) {
            // username exists
        }

        return login();
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(UserLoginBinding userLogin) {
        UserLoginBinding user = this.userService.findUserByUsername(userLogin.getUsername());

        if (user == null) {
            return login();
        } else if (!user.getPassword().equals(userLogin.getPassword())) {
            return login();
        }

        return "redirect:/";
    }
}
