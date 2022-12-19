package bg.softuni.springbootintroduction.web;

import bg.softuni.springbootintroduction.domain.binding.OfferSubmitBindingModel;
import bg.softuni.springbootintroduction.services.BrandService;
import bg.softuni.springbootintroduction.services.OfferService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/offers")
public class OfferController {

    private final OfferService offerService;
    private final BrandService brandService;

    @Autowired
    public OfferController(OfferService offerService, BrandService brandService) {
        this.offerService = offerService;
        this.brandService = brandService;
    }

    @ModelAttribute("offer")
    public OfferSubmitBindingModel initOfferModel() {
        return new OfferSubmitBindingModel();
    }

    @GetMapping("/add")
    public String showAddOffer(Model model) {
        int currentYear = LocalDate.now().getYear();

        model.addAttribute("brands", this.brandService.getAllBrands());
        model.addAttribute("currentYear", currentYear);

        return "offer-add";
    }

    @PostMapping("/add")
    public String addOffer(@Valid OfferSubmitBindingModel offerSubmitBindingModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("offer", offerSubmitBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.offer",
                    bindingResult);

            return "redirect:/offers/add";
        }

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
        model.addAttribute("canDelete", this.offerService.canCurrentUserModifyGivenOffer(id));
        model.addAttribute("canUpdate", this.offerService.canCurrentUserModifyGivenOffer(id));
        model.addAttribute("details", this.offerService.getOfferById(id));

        return "details";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        this.offerService.deleteOfferById(id);

        return "redirect:/offers/all";
    }

    @GetMapping("/update")
    public String showUpdate() {
        return "update";
    }

    @PatchMapping("/{id}/update")
    public String update(@PathVariable Long id) {
        //TODO do this

        return "redirect:/offers/all";
    }
}
