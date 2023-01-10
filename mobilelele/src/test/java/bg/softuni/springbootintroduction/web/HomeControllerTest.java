package bg.softuni.springbootintroduction.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {

    private static final String HOMEPAGE_URL = "/";

    private static final String VIEW_NAME = "index";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void test_Show_HomePage() throws Exception {
        mockMvc.perform(get(HOMEPAGE_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_NAME));
    }
}