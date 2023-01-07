package bg.softuni.springbootintroduction.service.impl;

import bg.softuni.springbootintroduction.domain.binding.OfferSubmitBindingModel;
import bg.softuni.springbootintroduction.domain.binding.OfferUpdateBindingModel;
import bg.softuni.springbootintroduction.domain.dto.OfferImportDTO;
import bg.softuni.springbootintroduction.domain.entity.Model;
import bg.softuni.springbootintroduction.domain.entity.Offer;
import bg.softuni.springbootintroduction.domain.entity.User;
import bg.softuni.springbootintroduction.domain.view.OfferDetailsViewModel;
import bg.softuni.springbootintroduction.domain.view.OfferViewModel;
import bg.softuni.springbootintroduction.repository.ModelRepository;
import bg.softuni.springbootintroduction.repository.OfferRepository;
import bg.softuni.springbootintroduction.repository.UserRepository;
import bg.softuni.springbootintroduction.service.OfferService;
import bg.softuni.springbootintroduction.service.exceptions.OfferNotFoundException;
import bg.softuni.springbootintroduction.utils.enums.Engine;
import bg.softuni.springbootintroduction.utils.enums.Role;
import bg.softuni.springbootintroduction.utils.enums.Transmission;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final ModelRepository modelRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, ModelRepository modelRepository, UserRepository userRepository, ModelMapper mapper) {
        this.offerRepository = offerRepository;
        this.modelRepository = modelRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
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

            User seller1 = this.userRepository.findUserByUsername(offer1.getSeller()).get();
            User seller2 = this.userRepository.findUserByUsername(offer2.getSeller()).get();

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
        return this.offerRepository
                .findById(id)
                .orElseThrow(() -> new OfferNotFoundException(id));
    }

    @Override
    public Optional<OfferDetailsViewModel> getOfferDetailsModelById(Long id) {
        Offer offer = getOfferById(id);

        return Optional.of(mapper.map(offer, OfferDetailsViewModel.class));
    }

    @Override
    public Optional<OfferUpdateBindingModel> getOfferUpdateModelById(Long id) {
        Offer offer = getOfferById(id);

        return Optional.of(mapper.map(offer, OfferUpdateBindingModel.class));
    }

    @Override
    public List<OfferViewModel> getAllOffers() {
        List<Offer> allOffers = this.offerRepository.findAll();

        return allOffers
                .stream()
                .map(o -> this.mapper.map(o, OfferViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void addOffer(OfferSubmitBindingModel offerSubmitBindingModel, Principal principal) {
        Model model = this.modelRepository
                .findFirstByName(offerSubmitBindingModel.getModel());

        Offer offer = this.mapper.map(offerSubmitBindingModel, Offer.class);

        offer.setModel(model);
        offer.setCreated(Instant.now());

        Optional<User> seller = this.userRepository.findUserByUsername(principal.getName());

        seller.ifPresent(offer::setSeller);

        this.offerRepository.save(offer);
    }

    @Override
    public boolean isOwner(String username, Long id) {
        Optional<Offer> offerOpt = this.offerRepository.findById(id);

        Optional<User> caller = this.userRepository.findUserByUsername(username);

        if (offerOpt.isEmpty() || caller.isEmpty()) {
            return false;
        }

        Offer offer = offerOpt.get();

        return isCurrentUserAdmin(caller.get()) ||
                offer.getSeller().getUsername().equalsIgnoreCase(username);
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

    private boolean isCurrentUserAdmin(User user) {
        return user.getRole().getRole().compareTo(Role.ADMIN) == 0;
    }
}
