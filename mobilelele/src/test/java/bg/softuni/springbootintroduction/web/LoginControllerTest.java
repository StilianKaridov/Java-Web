package bg.softuni.springbootintroduction.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {

    private static final String LOGIN_PAGE_URL = "/users/login";

    private static final String LOGIN_ERROR_PAGE_URL = "/users/login-error";

    private static final String VIEW_NAME = "auth-login";

    private static final String FLASH_BAD_CREDENTIALS_LABEL = "badCredentials";

    private static final String FLASH_USERNAME_LABEL = "username";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void test_Show_LoginPage() throws Exception {
        mockMvc.perform(get(LOGIN_PAGE_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_NAME));
    }

    @Test
    void test_FailedLogin() throws Exception {
        mockMvc.perform(post(LOGIN_ERROR_PAGE_URL)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists(FLASH_BAD_CREDENTIALS_LABEL))
                .andExpect(flash().attributeExists(FLASH_USERNAME_LABEL))
                .andExpect(redirectedUrl(LOGIN_PAGE_URL));
    }
}