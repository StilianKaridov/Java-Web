package bg.softuni.springbootintroduction.web;

import bg.softuni.springbootintroduction.domain.binding.UserRegisterBindingModel;
import bg.softuni.springbootintroduction.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class RegisterController {

    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegister() {
        return "auth-register";
    }

    @PostMapping("/register")
    public String register(UserRegisterBindingModel user) {
        if (this.userService.isUsernameFree(user.getUsername())) {
            this.userService.register(user);
            return "redirect:/users/login";
        }

        return "redirect:/users/register";
    }
}
