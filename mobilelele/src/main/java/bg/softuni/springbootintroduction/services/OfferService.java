package bg.softuni.springbootintroduction.services;

import bg.softuni.springbootintroduction.domain.binding.OfferSubmitBindingModel;
import bg.softuni.springbootintroduction.domain.binding.OfferUpdateBindingModel;
import bg.softuni.springbootintroduction.domain.entity.Offer;
import bg.softuni.springbootintroduction.domain.view.OfferDetailsViewModel;
import bg.softuni.springbootintroduction.domain.view.OfferViewModel;

import java.util.List;
import java.util.Optional;

public interface OfferService {

    void seedOffers();

    Offer getOfferById(Long id);

    Optional<OfferDetailsViewModel> getOfferDetailsModelById(Long id);

    Optional<OfferUpdateBindingModel> getOfferUpdateModelById(Long id);

    List<OfferViewModel> getAllOffers();

    void addOffer(OfferSubmitBindingModel offerSubmitBindingModel);

    void deleteOfferById(Long id);

    void updateOffer(OfferUpdateBindingModel offerUpdateBindingModel);

    boolean isCurrentUserAdmin();

    boolean canCurrentUserModifyGivenOffer(Long offerId);
}
