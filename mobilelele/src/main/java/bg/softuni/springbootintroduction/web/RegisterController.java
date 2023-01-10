package bg.softuni.springbootintroduction.web;

import bg.softuni.springbootintroduction.domain.binding.UserRegisterBindingModel;
import bg.softuni.springbootintroduction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class RegisterController {

    private static final String USER_LABEL = "user";
    private static final String BINDING_RESULT_LABEL = "org.springframework.validation.BindingResult.user";

    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute(USER_LABEL)
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
            redirectAttributes.addFlashAttribute(USER_LABEL, user);
            redirectAttributes.addFlashAttribute(BINDING_RESULT_LABEL, bindingResult);

            return "redirect:/users/register";
        }

        this.userService.register(user);

        return "redirect:/";
    }
}
