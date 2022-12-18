package bg.softuni.springbootintroduction.web;

import bg.softuni.springbootintroduction.domain.binding.UserLoginBindingModel;
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
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserLoginBindingModel initUserModel() {
        return new UserLoginBindingModel();
    }


    @GetMapping("/login")
    public String showLogin() {
        return "auth-login";
    }

    @PostMapping("/login")
    public String login(@Valid UserLoginBindingModel user,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes) {

        //TODO this is the functionality only for when the input's are empty, make it to work and for invalid username/password
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user",
                    bindingResult);

            return "redirect:/users/login";
        }

        this.userService.loginUser(user.getUsername());

        return "redirect:/";

//        if (this.userService.authenticate(user.getUsername(), user.getPassword())) {
//            this.userService.loginUser(user.getUsername());
//            return "redirect:/";
//        }
//
//        return "redirect:/users/login";
    }
}
