package bg.softuni.springbootintroduction.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class StatsControllerTest {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ROLE_ADMIN_NAME = "ADMIN";

    private static final String STATISTICS_URL = "/statistics";

    private static final String VIEW_AND_ATTRIBUTE_NAME = "stats";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = ADMIN_USERNAME, roles = {ROLE_ADMIN_NAME})
    void test_Show_StatisticsPage() throws Exception {
        mockMvc.perform(get(STATISTICS_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_AND_ATTRIBUTE_NAME))
                .andExpect(model().attributeExists(VIEW_AND_ATTRIBUTE_NAME));
    }
}