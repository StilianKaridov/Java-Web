package bg.softuni.springbootintroduction.services.impl;

import bg.softuni.springbootintroduction.domain.binding.OfferSubmitBindingModel;
import bg.softuni.springbootintroduction.domain.binding.OfferUpdateBindingModel;
import bg.softuni.springbootintroduction.domain.dto.OfferImportDTO;
import bg.softuni.springbootintroduction.domain.entity.Model;
import bg.softuni.springbootintroduction.domain.entity.Offer;
import bg.softuni.springbootintroduction.domain.entity.User;
import bg.softuni.springbootintroduction.domain.view.OfferDetailsViewModel;
import bg.softuni.springbootintroduction.domain.view.OfferViewModel;
import bg.softuni.springbootintroduction.repositories.ModelRepository;
import bg.softuni.springbootintroduction.repositories.OfferRepository;
import bg.softuni.springbootintroduction.repositories.UserRepository;
import bg.softuni.springbootintroduction.security.CurrentUser;
import bg.softuni.springbootintroduction.services.OfferService;
import bg.softuni.springbootintroduction.utils.enums.Engine;
import bg.softuni.springbootintroduction.utils.enums.Role;
import bg.softuni.springbootintroduction.utils.enums.Transmission;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final ModelRepository modelRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final CurrentUser currentUser;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, ModelRepository modelRepository, UserRepository userRepository, ModelMapper mapper, CurrentUser currentUser) {
        this.offerRepository = offerRepository;
        this.modelRepository = modelRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.currentUser = currentUser;
    }

    @Override
    public void seedOffers() {
        if (this.offerRepository.count() == 0) {
            OfferImportDTO offer1 = new OfferImportDTO(Engine.DIESEL, "https://felix-airsuspension.com/wp-content/uploads/2019/01/2014_mercedes-benz_cls-classjpeg-1024x781.jpeg", BigDecimal.valueOf(30000), Transmission.AUTOMATIC, 2011, Instant.now(), "CLS-Class", "ivan0123");
            OfferImportDTO offer2 = new OfferImportDTO(Engine.GASOLINE, "https://cdn3.focus.bg/autodata/i/audi/a4/a4-b8/large/4e3dc942a9585e01775d66cf0163a22b.jpg", BigDecimal.valueOf(25000), Transmission.MANUAL, 2008, Instant.now(), "A4", "toshko0101");

            Offer toInsert = this.mapper.map(offer1, Offer.class);
            Offer toInsert2 = this.mapper.map(offer2, Offer.class);

            Model model1 = this.modelRepository.findFirstByName(offer1.getModel());
            Model model2 = this.modelRepository.findFirstByName(offer2.getModel());

            User seller1 = this.userRepository.findFirstByUsernameIgnoreCase(offer1.getSeller()).get();
            User seller2 = this.userRepository.findFirstByUsernameIgnoreCase(offer2.getSeller()).get();

            toInsert.setModel(model1);
            toInsert.setSeller(seller1);

            toInsert2.setModel(model2);
            toInsert2.setSeller(seller2);

            this.offerRepository.saveAndFlush(toInsert);
            this.offerRepository.saveAndFlush(toInsert2);
        }
    }

    @Override
    public Offer getOfferById(Long id) {
        Optional<Offer> offerById = this.offerRepository.findById(id);

        return offerById.orElse(null);
    }

    @Override
    public Optional<OfferDetailsViewModel> getOfferDetailsModelById(Long id) {
        return this.offerRepository.findById(id)
                .map(o -> mapper.map(o, OfferDetailsViewModel.class));
    }

    @Override
    public Optional<OfferUpdateBindingModel> getOfferUpdateModelById(Long id) {
        return this.offerRepository.findById(id)
                .map(o -> mapper.map(o, OfferUpdateBindingModel.class));
    }

    @Override
    public List<OfferViewModel> getAllOffers() {
        List<Offer> allOffers = this.offerRepository.findAll();

        return allOffers
                .stream()
                .map(o -> this.mapper.map(o, OfferViewModel.class))
                .toList();
    }

    @Override
    public void addOffer(OfferSubmitBindingModel offerSubmitBindingModel) {
        Model model = this.modelRepository
                .findFirstByName(offerSubmitBindingModel.getModel());

        Offer offer = this.mapper.map(offerSubmitBindingModel, Offer.class);

        offer.setModel(model);
        offer.setCreated(Instant.now());

        Optional<User> seller = this.userRepository.findFirstByUsernameIgnoreCase(currentUser.getUsername());

        seller.ifPresent(offer::setSeller);

        this.offerRepository.save(offer);
    }

    @Override
    public void deleteOfferById(Long id) {
        this.offerRepository.deleteById(id);
    }

    @Override
    public void updateOffer(OfferUpdateBindingModel offerUpdateBindingModel) {
        Offer offer = getOfferById(offerUpdateBindingModel.getId());

        offer.setPrice(offerUpdateBindingModel.getPrice());
        offer.setImageUrl(offerUpdateBindingModel.getImageUrl());
        offer.setYear(offerUpdateBindingModel.getYear());
        offer.setMileage(offerUpdateBindingModel.getMileage());
        offer.setDescription(offerUpdateBindingModel.getDescription());
        offer.setModified(Instant.now());

        this.offerRepository.save(offer);
    }

    @Override
    public boolean isCurrentUserAdmin() {
        Optional<User> loggedInUser = this.userRepository.findFirstByUsernameIgnoreCase(currentUser.getUsername());

        if (loggedInUser.isEmpty()) {
            return false;
        }

        Role loggedInUserRole = loggedInUser.get().getRole().getRole();

        return loggedInUserRole.equals(Role.Admin);
    }

    @Override
    public boolean canCurrentUserModifyGivenOffer(Long offerId) {
        Offer offer = getOfferById(offerId);


        OfferDetailsViewModel offerDetails = mapper.map(offer, OfferDetailsViewModel.class);
        ;

        boolean isAnonymous = currentUser.getUsername().equals("anonymous");

        if (offerDetails.getSellerUsername() == null && isAnonymous) {
            return true;
        } else if (offerDetails.getSellerUsername() == null && isCurrentUserAdmin()) {
            return true;
        } else if (offerDetails.getSellerUsername() == null && !isAnonymous) {
            return false;
        }

        return isCurrentUserAdmin() || offerDetails.getSellerUsername().equals(currentUser.getUsername());
    }
}
