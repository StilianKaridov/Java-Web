package bg.softuni.springbootintroduction.web;

import bg.softuni.springbootintroduction.domain.binding.UserRegisterBindingModel;
import bg.softuni.springbootintroduction.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class RegisterController {

    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserRegisterBindingModel initUserModel() {
        return new UserRegisterBindingModel();
    }

    @GetMapping("/register")
    public String showRegister() {
        return "auth-register";
    }

    @PostMapping("/register")
    public String register(@Valid UserRegisterBindingModel user,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user",
                    bindingResult);

            return "redirect:/users/register";
        }

        this.userService.register(user);

        return "redirect:/users/login";
    }
}
