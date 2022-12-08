package bg.softuni.springbootintroduction.web;

import bg.softuni.springbootintroduction.domain.binding.UserLoginBindingModel;
import bg.softuni.springbootintroduction.domain.binding.UserRegisterBindingModel;
import bg.softuni.springbootintroduction.services.UserService;
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
    public String registerUser() {
        return "auth-register";
    }

    @PostMapping("/register")
    public String register(UserRegisterBindingModel userRegister) {

        if (this.userService.isUsernameFree(userRegister.getUsername())) {
            this.userService.register(userRegister);
            return "redirect:/";
        } else {
            return "redirect:/users/register";
        }
    }

    @GetMapping("/login")
    public String loginUser() {
        return "auth-login";
    }

    @PostMapping("/login")
    public String login(UserLoginBindingModel userLogin) {

        if (!this.userService.authenticateUser(userLogin)) {
            return "redirect:/users/login";
        }

        this.userService.login(userLogin);
        return "redirect:/";
    }
}
