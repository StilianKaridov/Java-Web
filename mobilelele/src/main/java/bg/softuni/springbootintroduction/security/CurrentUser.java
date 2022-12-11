package bg.softuni.springbootintroduction.security;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class CurrentUser {

    private static final String ANONYMOUS = "anonymous";

    private String username = ANONYMOUS;
    private boolean isAnonymous;

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous) {
        if (anonymous) {
            this.username = ANONYMOUS;
        }

        isAnonymous = anonymous;
    }
}
