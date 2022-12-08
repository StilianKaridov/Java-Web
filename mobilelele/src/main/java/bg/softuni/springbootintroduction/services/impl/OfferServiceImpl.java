package bg.softuni.springbootintroduction.services.impl;

import bg.softuni.springbootintroduction.domain.dto.OfferImportDTO;
import bg.softuni.springbootintroduction.domain.entity.Model;
import bg.softuni.springbootintroduction.domain.entity.Offer;
import bg.softuni.springbootintroduction.domain.entity.User;
import bg.softuni.springbootintroduction.repositories.ModelRepository;
import bg.softuni.springbootintroduction.repositories.OfferRepository;
import bg.softuni.springbootintroduction.repositories.UserRepository;
import bg.softuni.springbootintroduction.services.OfferService;
import bg.softuni.springbootintroduction.utils.enums.Engine;
import bg.softuni.springbootintroduction.utils.enums.Transmission;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;

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
            OfferImportDTO offer1 = new OfferImportDTO(Engine.DIESEL, "url1", BigDecimal.valueOf(30000), Transmission.AUTOMATIC, 2011, Instant.now(), "CLS-Class", "ivan0123");
            OfferImportDTO offer2 = new OfferImportDTO(Engine.GASOLINE, "url2", BigDecimal.valueOf(25000), Transmission.MANUAL, 2008, Instant.now(), "A4", "toshko0101");

            Offer toInsert = this.mapper.map(offer1, Offer.class);
            Offer toInsert2 = this.mapper.map(offer2, Offer.class);

            Model model1 = this.modelRepository.findFirstByName(offer1.getModel());
            Model model2 = this.modelRepository.findFirstByName(offer2.getModel());

            User seller1 = this.userRepository.findFirstByUsername(offer1.getSeller()).get();
            User seller2 = this.userRepository.findFirstByUsername(offer2.getSeller()).get();

            toInsert.setModel(model1);
            toInsert.setSeller(seller1);

            toInsert2.setModel(model2);
            toInsert2.setSeller(seller2);

            this.offerRepository.saveAndFlush(toInsert);
            this.offerRepository.saveAndFlush(toInsert2);
        }
    }
}
