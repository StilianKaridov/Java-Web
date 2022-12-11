package bg.softuni.springbootintroduction.web;

import bg.softuni.springbootintroduction.domain.binding.UserLoginBindingModel;
import bg.softuni.springbootintroduction.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/login")
    public String showLogin() {
        return "auth-login";
    }

    @PostMapping("/users/login")
    public String login(UserLoginBindingModel user) {
        if (this.userService.authenticate(user.getUsername(), user.getPassword())) {
            this.userService.loginUser(user.getUsername());
            return "redirect:/";
        }

        return "redirect:/users/login";
    }
}
