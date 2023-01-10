package bg.softuni.springbootintroduction.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BrandControllerTest {

    private static final String BRANDS_URL = "/brands/all";

    private static final String VIEW_AND_ATTRIBUTE_NAME = "brands";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void test_Show_BrandsPage() throws Exception {
        mockMvc.perform(get(BRANDS_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_AND_ATTRIBUTE_NAME))
                .andExpect(model().attributeExists(VIEW_AND_ATTRIBUTE_NAME));
    }
}