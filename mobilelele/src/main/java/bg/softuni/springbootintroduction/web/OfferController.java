package bg.softuni.springbootintroduction.web;

import bg.softuni.springbootintroduction.domain.binding.OfferSubmitBindingModel;
import bg.softuni.springbootintroduction.services.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/offers")
public class OfferController {

    private final OfferService offerService;

    @Autowired
    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/add")
    public String showAddOffer() {
        return "offer-add";
    }

    @PostMapping("/add")
    public String addOffer(OfferSubmitBindingModel offerSubmitBindingModel) {
        this.offerService.addOffer(offerSubmitBindingModel);

        return "redirect:/";
    }

    @GetMapping("/all")
    public String showAllOffers(Model model) {
        model.addAttribute("offers", this.offerService.getAllOffers());

        return "offers";
    }

    @GetMapping("/{id}/details")
    public String showDetails(@PathVariable Long id, Model model) {
        model.addAttribute("details", this.offerService.getByOfferById(id));

        return "details";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        this.offerService.deleteOfferById(id);

        return "redirect:/offers/all";
    }
}
