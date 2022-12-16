package bg.softuni.springbootintroduction.services.impl;

import bg.softuni.springbootintroduction.domain.binding.OfferSubmitBindingModel;
import bg.softuni.springbootintroduction.domain.dto.OfferImportDTO;
import bg.softuni.springbootintroduction.domain.entity.Model;
import bg.softuni.springbootintroduction.domain.entity.Offer;
import bg.softuni.springbootintroduction.domain.entity.User;
import bg.softuni.springbootintroduction.domain.view.OfferViewModel;
import bg.softuni.springbootintroduction.repositories.ModelRepository;
import bg.softuni.springbootintroduction.repositories.OfferRepository;
import bg.softuni.springbootintroduction.repositories.UserRepository;
import bg.softuni.springbootintroduction.security.CurrentUser;
import bg.softuni.springbootintroduction.services.OfferService;
import bg.softuni.springbootintroduction.utils.enums.Engine;
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
            OfferImportDTO offer1 = new OfferImportDTO(Engine.DIESEL, "url1", BigDecimal.valueOf(30000), Transmission.AUTOMATIC, 2011, Instant.now(), "CLS-Class", "ivan0123");
            OfferImportDTO offer2 = new OfferImportDTO(Engine.GASOLINE, "url2", BigDecimal.valueOf(25000), Transmission.MANUAL, 2008, Instant.now(), "A4", "toshko0101");

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
    public List<OfferViewModel> getAllOffers() {
        List<Offer> allOffers = this.offerRepository.findAll();

        return allOffers
                .stream()
                .map(o -> this.mapper.map(o, OfferViewModel.class))
                .toList();
    }
}
