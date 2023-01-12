package bg.softuni.springbootintroduction.web;

import bg.softuni.springbootintroduction.domain.entity.User;
import bg.softuni.springbootintroduction.domain.entity.UserRole;
import bg.softuni.springbootintroduction.repository.UserRepository;
import bg.softuni.springbootintroduction.repository.UserRoleRepository;
import bg.softuni.springbootintroduction.utils.enums.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RegisterControllerTest {

    private static final String VIEW_NAME = "auth-register";

    private static final String HOMEPAGE_URL = "/";
    private static final String REGISTER_URL = "/users/register";

    private static final String USER_FIRST_NAME_LABEL = "firstName";
    private static final String USER_FIRST_NAME = "Ivan";

    private static final String USER_LAST_NAME_LABEL = "lastName";
    private static final String USER_LAST_NAME = "Ivanov";

    private static final String USER_USERNAME_LABEL = "username";
    private static final String USER_USERNAME = "ivan2023";

    private static final String USER_PASSWORD_LABEL = "password";
    private static final String USER_PASSWORD = "test2023";
    private static final String USER_INVALID_PASSWORD = "fail";

    private static final String USER_FLASH_ATTRIBUTE_NAME = "user";
    private static final String BINDING_RESULT_FLASH_ATTRIBUTE_NAME = "org.springframework.validation.BindingResult.user";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @BeforeEach
    void setUp() {
        UserRole roleAdmin = new UserRole(Role.ADMIN);
        UserRole roleUser = new UserRole(Role.USER);

        userRoleRepository.save(roleAdmin);
        userRoleRepository.save(roleUser);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void test_Show_RegistrationPage() throws Exception {
        mockMvc.perform(get(REGISTER_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_NAME));
    }

    @Test
    void test_UserRegistration_Successfully() throws Exception {
        mockMvc.perform(post(REGISTER_URL)
                        .param(USER_FIRST_NAME_LABEL, USER_FIRST_NAME)
                        .param(USER_LAST_NAME_LABEL, USER_LAST_NAME)
                        .param(USER_USERNAME_LABEL, USER_USERNAME)
                        .param(USER_PASSWORD_LABEL, USER_PASSWORD)
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(HOMEPAGE_URL));

        assertEquals(1, userRepository.count());

        Optional<User> newlyCreatedUserOpt = userRepository.findUserByUsername(USER_USERNAME);

        assertTrue(newlyCreatedUserOpt.isPresent());

        User newlyCreatedUser = newlyCreatedUserOpt.get();

        assertEquals(USER_LAST_NAME, newlyCreatedUser.getLastName());
        assertEquals(USER_USERNAME, newlyCreatedUser.getUsername());
    }

    @Test
    void test_UserRegistration_Failed() throws Exception {
        mockMvc.perform(post(REGISTER_URL)
                        .param(USER_FIRST_NAME_LABEL, USER_FIRST_NAME)
                        .param(USER_LAST_NAME_LABEL, USER_LAST_NAME)
                        .param(USER_USERNAME_LABEL, USER_USERNAME)
                        .param(USER_PASSWORD_LABEL, USER_INVALID_PASSWORD)
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(REGISTER_URL))
                .andExpect(flash().attributeExists(USER_FLASH_ATTRIBUTE_NAME))
                .andExpect(flash().attributeExists(BINDING_RESULT_FLASH_ATTRIBUTE_NAME));
    }
}