package bg.softuni.springbootintroduction.web;

import bg.softuni.springbootintroduction.domain.entity.Brand;
import bg.softuni.springbootintroduction.domain.entity.Model;
import bg.softuni.springbootintroduction.repository.BrandRepository;
import bg.softuni.springbootintroduction.repository.ModelRepository;
import bg.softuni.springbootintroduction.utils.enums.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.HashSet;

import static org.hamcrest.Matchers.aMapWithSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = "user")
@SpringBootTest
@AutoConfigureMockMvc
class OfferRestControllerTest {

    private static final String BRAND_NAME = "Audi";

    private static final String MODEL_NAME = "A4";
    private static final Integer MODEL_START_YEAR = 2010;
    private static final Integer MODEL_END_YEAR = 2016;

    private static final String GET_MODEL_PREFIX_URL = "/offers/add/";
    private static final String GET_MODEL_ONUPDATE_PREFIX_URL = "/offers/updateModel/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private BrandRepository brandRepository;

    @BeforeEach
    void setUp() {
        Brand brand = new Brand(BRAND_NAME, Instant.now(), Instant.now(), new HashSet<>());

        brandRepository.save(brand);

        Model testModel = new Model();
        testModel.setName(MODEL_NAME);
        testModel.setCategory(Category.Car);
        testModel.setStartYear(MODEL_START_YEAR);
        testModel.setEndYear(MODEL_END_YEAR);
        testModel.setBrand(brand);

        modelRepository.save(testModel);
    }

    @AfterEach
    void tearDown() {
        modelRepository.deleteAll();
    }

    @Test
    void test_GetModel_WithManufacturingYears() throws Exception {
        Model model = modelRepository.findFirstByName(MODEL_NAME);

        mockMvc.perform(get(GET_MODEL_PREFIX_URL + model.getName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", aMapWithSize(3)));
    }

    @Test
    void test_GetModel_WithManufacturingYears_OnUpdate() throws Exception {
        Model model = modelRepository.findFirstByName(MODEL_NAME);

        mockMvc.perform(get(GET_MODEL_ONUPDATE_PREFIX_URL + model.getName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", aMapWithSize(3)));
    }
}