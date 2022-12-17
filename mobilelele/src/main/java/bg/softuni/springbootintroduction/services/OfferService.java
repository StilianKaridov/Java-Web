package bg.softuni.springbootintroduction.services;

import bg.softuni.springbootintroduction.domain.binding.OfferSubmitBindingModel;
import bg.softuni.springbootintroduction.domain.view.OfferDetailsViewModel;
import bg.softuni.springbootintroduction.domain.view.OfferViewModel;

import java.util.List;

public interface OfferService {

    void seedOffers();

    void addOffer(OfferSubmitBindingModel offerSubmitBindingModel);

    List<OfferViewModel> getAllOffers();

    OfferDetailsViewModel getByOfferById(Long id);
}
