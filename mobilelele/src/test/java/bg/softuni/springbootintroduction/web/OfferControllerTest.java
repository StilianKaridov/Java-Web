package bg.softuni.springbootintroduction.web;

import bg.softuni.springbootintroduction.domain.entity.Model;
import bg.softuni.springbootintroduction.domain.entity.Offer;
import bg.softuni.springbootintroduction.domain.entity.User;
import bg.softuni.springbootintroduction.domain.entity.UserRole;
import bg.softuni.springbootintroduction.repository.ModelRepository;
import bg.softuni.springbootintroduction.repository.OfferRepository;
import bg.softuni.springbootintroduction.repository.UserRepository;
import bg.softuni.springbootintroduction.repository.UserRoleRepository;
import bg.softuni.springbootintroduction.service.exceptions.OfferNotFoundException;
import bg.softuni.springbootintroduction.utils.enums.Category;
import bg.softuni.springbootintroduction.utils.enums.Engine;
import bg.softuni.springbootintroduction.utils.enums.Role;
import bg.softuni.springbootintroduction.utils.enums.Transmission;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "loggedInUser")
class OfferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    private Model testModel;

    private User testUser;

    private Offer testOffer;

    @BeforeEach
    void setUp() {
        testModel = new Model();
        testModel.setName("A4");
        testModel.setCategory(Category.Car);
        testModel.setStartYear(2010);
        testModel.setEndYear(2016);

        modelRepository.save(testModel);

        UserRole adminRole = new UserRole(Role.ADMIN);
        UserRole userRole = new UserRole(Role.USER);

        userRoleRepository.save(adminRole);
        userRoleRepository.save(userRole);

        testUser = new User();
        testUser.setUsername("loggedInUser");
        testUser.setPassword("testPassword");
        testUser.setFirstName("Logged");
        testUser.setLastName("User");
        testUser.setActive(true);
        testUser.setRole(userRole);
        testUser.setCreated(Instant.now());

        userRepository.save(testUser);

        testOffer = new Offer();
        testOffer.setModel(testModel);
        testOffer.setSeller(testUser);
        testOffer.setEngine(Engine.DIESEL);
        testOffer.setImageUrl("image.com/image");
        testOffer.setPrice(BigDecimal.TEN);
        testOffer.setTransmission(Transmission.AUTOMATIC);
        testOffer.setYear(2015);
        testOffer.setCreated(Instant.now());

        offerRepository.save(testOffer);
    }

    @AfterEach
    void tearDown() {
        offerRepository.deleteAll();
        modelRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void test_Show_AddOfferPage() throws Exception {
        mockMvc.perform(get("/offers/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("offer-add"))
                .andExpect(model().attributeExists("brands"));
    }

    @Test
    void test_AddOffer_Successfully() throws Exception {
        mockMvc.perform(post("/offers/add")
                        .param("model", "A4")
                        .param("price", String.valueOf(BigDecimal.TEN))
                        .param("engine", "DIESEL")
                        .param("transmission", "AUTOMATIC")
                        .param("year", String.valueOf(2015))
                        .param("mileage", String.valueOf(1500))
                        .param("description", "Description to the offer")
                        .param("imageUrl", "image.com/image")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        assertEquals(2, offerRepository.count());
    }

    @Test
    void test_AddOffer_Failed() throws Exception {
        mockMvc.perform(post("/offers/add")
                        .param("model", "A4")
                        .param("price", String.valueOf(BigDecimal.ZERO))
                        .param("engine", "DIESEL")
                        .param("transmission", "AUTOMATIC")
                        .param("year", String.valueOf(2015))
                        .param("mileage", String.valueOf(1500))
                        .param("description", "Description to the offer")
                        .param("imageUrl", "image.com/image")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("offer"))
                .andExpect(flash().attributeExists("org.springframework.validation.BindingResult.offer"))
                .andExpect(redirectedUrl("/offers/add"));
    }

    @Test
    void test_Show_AllOffers() throws Exception {
        mockMvc.perform(get("/offers/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("offers"))
                .andExpect(model().attributeExists("offers"));
    }

    @Test
    void test_Show_OfferDetails_CanModify() throws Exception {
        mockMvc.perform(get("/offers/details/" + testOffer.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("offerDetails"))
                .andExpect(model().attribute("canModify", true))
                .andExpect(view().name("details"));
    }

    @WithMockUser(username = "NoAuthorityToModify")
    @Test
    void test_Show_OfferDetails_CannotModify() throws Exception {
        mockMvc.perform(get("/offers/details/" + testOffer.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("offerDetails"))
                .andExpect(model().attribute("canModify", false))
                .andExpect(view().name("details"));
    }

    @Test
    void test_DeleteOffer() throws Exception {
        mockMvc.perform(delete("/offers/delete/" + testOffer.getId())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/offers/all"));

        assertEquals(0, offerRepository.count());
    }

    @Test
    void test_Show_UpdateErrorsPage() throws Exception {
        mockMvc.perform(get("/offers/update/" + testOffer.getId() + "/errors"))
                .andExpect(status().isOk())
                .andExpect(view().name("update"));
    }

    @Test
    void test_Show_UpdatePage() throws Exception {
        mockMvc.perform(get("/offers/update/" + testOffer.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("offerUpdate"))
                .andExpect(view().name("update"));
    }

    @Test
    void test_UpdateOffer_Successfully() throws Exception {
        mockMvc.perform(patch("/offers/update/" + testOffer.getId())
                        .param("price", String.valueOf(BigDecimal.valueOf(5)))
                        .param("imageUrl", "image.com/image")
                        .param("year", String.valueOf(2015))
                        .param("mileage", String.valueOf(1000))
                        .param("description", "Used for 1000km.")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/offers/all"));
    }

    @Test
    void test_UpdateOffer_Failed() throws Exception {
        mockMvc.perform(patch("/offers/update/" + testOffer.getId())
                        .param("price", String.valueOf(BigDecimal.ZERO))
                        .param("imageUrl", "image.com/image")
                        .param("year", String.valueOf(2015))
                        .param("mileage", String.valueOf(1000))
                        .param("description", "Used for 1000km.")
                        .with(csrf()))
                .andExpect(flash().attributeExists("offerUpdate"))
                .andExpect(flash().attributeExists("org.springframework.validation.BindingResult.offerUpdate"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/offers/update/" + testOffer.getId() + "/errors"));
    }

    @Test
    void test_handleOfferException() throws Exception {
        long invalidId = this.offerRepository.count() + 1;

        mockMvc.perform(get("/offers/details/" + invalidId))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof OfferNotFoundException))
                .andExpect(view().name("offer-not-found"))
                .andExpect(model().attributeExists("offerId"));
    }
}