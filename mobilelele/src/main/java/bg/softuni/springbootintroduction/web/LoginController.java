package bg.softuni.springbootintroduction.web;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class LoginController {

    private static final String BAD_CREDENTIALS_LABEL = "badCredentials";
    private static final String USERNAME_LABEL = "username";

    @GetMapping("/login")
    public String showLogin() {
        return "auth-login";
    }

    @PostMapping("/login-error")
    public String failedLogin (
            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
            String username,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute(BAD_CREDENTIALS_LABEL, true);
        redirectAttributes.addFlashAttribute(USERNAME_LABEL, username);

        return "redirect:/users/login";
    }
}
