package bg.softuni.springbootintroduction.service.impl;

import bg.softuni.springbootintroduction.domain.binding.OfferSubmitBindingModel;
import bg.softuni.springbootintroduction.domain.binding.OfferUpdateBindingModel;
import bg.softuni.springbootintroduction.domain.entity.Model;
import bg.softuni.springbootintroduction.domain.entity.Offer;
import bg.softuni.springbootintroduction.domain.entity.User;
import bg.softuni.springbootintroduction.domain.entity.UserRole;
import bg.softuni.springbootintroduction.domain.view.OfferDetailsViewModel;
import bg.softuni.springbootintroduction.domain.view.OfferViewModel;
import bg.softuni.springbootintroduction.repository.ModelRepository;
import bg.softuni.springbootintroduction.repository.OfferRepository;
import bg.softuni.springbootintroduction.repository.UserRepository;
import bg.softuni.springbootintroduction.service.OfferService;
import bg.softuni.springbootintroduction.service.exceptions.OfferNotFoundException;
import bg.softuni.springbootintroduction.utils.enums.Role;
import com.sun.security.auth.UserPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OfferServiceImplTest {

    private final String MODEL_NAME = "Audi";

    private final String OFFER_OWNER_NAME = "Owner";

    private final String NOT_OWNER_SELLER_NAME = "NotOwner";

    private final String NON_EXISTING_USER_NAME = "NonExistingUserUsername";

    private final String OFFER_DESCRIPTION = "offer1";

    private final Long OFFER_ID = 1L;

    private final Long INVALID_OFFER_ID = 1000L;

    private OfferService offerService;

    @Mock
    private OfferRepository mockedOfferRepository;

    @Mock
    private ModelRepository modelRepository;

    @Mock
    private UserRepository userRepository;

    private Offer offerToTest;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ModelMapper mapper = new ModelMapper();

        this.offerService = new OfferServiceImpl(
                mockedOfferRepository,
                modelRepository,
                userRepository,
                mapper
        );

        offerToTest = new Offer();
        offerToTest.setId(OFFER_ID);

        this.user = new User();
        user.setUsername(OFFER_OWNER_NAME);
        user.setRole(new UserRole(Role.USER));

        offerToTest.setSeller(user);
        offerToTest.setDescription(OFFER_DESCRIPTION);
    }

    @Test
    void test_FindById_ShouldReturn_CorrectOffer() {
        when(mockedOfferRepository.findById(OFFER_ID))
                .thenReturn(Optional.of(offerToTest));

        Offer offerById = this.offerService.getOfferById(OFFER_ID);

        assertEquals(OFFER_ID, offerById.getId());
    }

    @Test
    void test_FindById_ShouldThrow() {
        when(mockedOfferRepository.findById(INVALID_OFFER_ID))
                .thenThrow(OfferNotFoundException.class);

        assertThrows(
                OfferNotFoundException.class,
                () -> this.offerService.getOfferById(INVALID_OFFER_ID)
        );
    }

    @Test
    void test_IsOwner_WithRoleUser_ShouldReturn_True() {
        when(mockedOfferRepository.findById(OFFER_ID))
                .thenReturn(Optional.of(offerToTest));

        when(userRepository.findUserByUsername(OFFER_OWNER_NAME))
                .thenReturn(Optional.of(user));

        boolean isOwner = this.offerService.isOwner(user.getUsername(), OFFER_ID);

        assertTrue(isOwner);
    }

    @Test
    void test_IsOwner_WithRoleUser_ShouldReturn_False() {
        when(mockedOfferRepository.findById(OFFER_ID))
                .thenReturn(Optional.of(offerToTest));

        User notOwner = new User();
        notOwner.setUsername(NOT_OWNER_SELLER_NAME);
        notOwner.setRole(new UserRole(Role.USER));

        when(userRepository.findUserByUsername(NOT_OWNER_SELLER_NAME))
                .thenReturn(Optional.of(notOwner));

        boolean isOwner = this.offerService.isOwner(notOwner.getUsername(), OFFER_ID);

        assertFalse(isOwner);
    }

    @Test
    void test_IsOwner_WithRoleAdmin_ShouldReturn_True() {
        when(mockedOfferRepository.findById(OFFER_ID))
                .thenReturn(Optional.of(offerToTest));

        User admin = new User();
        admin.setUsername(NOT_OWNER_SELLER_NAME);
        admin.setRole(new UserRole(Role.ADMIN));

        offerToTest.setSeller(admin);

        when(userRepository.findUserByUsername(NOT_OWNER_SELLER_NAME))
                .thenReturn(Optional.of(admin));

        boolean isOwner = this.offerService.isOwner(admin.getUsername(), OFFER_ID);

        assertTrue(isOwner);
    }

    @Test
    void test_IsOwner_WithNonExistingUser_ShouldReturn_False() {
        when(mockedOfferRepository.findById(OFFER_ID))
                .thenReturn(Optional.of(offerToTest));

        when(userRepository.findUserByUsername(NON_EXISTING_USER_NAME))
                .thenReturn(Optional.empty());

        boolean isOwner = this.offerService.isOwner(NON_EXISTING_USER_NAME, OFFER_ID);

        assertFalse(isOwner);
    }

    @Test
    void test_GetOfferDetailsModelById_ReturnsProperOffer() {
        when(mockedOfferRepository.findById(OFFER_ID))
                .thenReturn(Optional.of(offerToTest));

        OfferDetailsViewModel offer = this.offerService
                .getOfferDetailsModelById(offerToTest.getId()).get();

        assertEquals(this.offerToTest.getId(), offer.getId());

        assertEquals(
                this.offerToTest.getSeller().getUsername(),
                offer.getSellerUsername()
        );
    }

    @Test
    void test_GetOfferUpdateModelById_ReturnsProperOffer() {
        when(mockedOfferRepository.findById(OFFER_ID))
                .thenReturn(Optional.of(offerToTest));

        OfferUpdateBindingModel offer = this.offerService
                .getOfferUpdateModelById(offerToTest.getId()).get();

        assertEquals(this.offerToTest.getId(), offer.getId());
    }

    @Test
    void test_GetAllOffers_ShouldReturn_Two_Offers() {
        Offer offer1 = new Offer();
        offer1.setId(1L);

        Offer offer2 = new Offer();
        offer2.setId(2L);

        List<Offer> preparedOffers = List.of(offer1, offer2);

        when(mockedOfferRepository.findAll()).thenReturn(preparedOffers);

        List<OfferViewModel> allOffers = this.offerService.getAllOffers();

        assertEquals(preparedOffers.size(), allOffers.size());

        for (int i = 0; i < allOffers.size(); i++) {
            assertEquals(
                    preparedOffers.get(i).getId(),
                    allOffers.get(i).getId()
            );
        }
    }
}