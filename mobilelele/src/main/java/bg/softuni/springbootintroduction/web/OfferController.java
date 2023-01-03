package bg.softuni.springbootintroduction.web;

import bg.softuni.springbootintroduction.domain.binding.OfferSubmitBindingModel;
import bg.softuni.springbootintroduction.domain.binding.OfferUpdateBindingModel;
import bg.softuni.springbootintroduction.domain.view.OfferDetailsViewModel;
import bg.softuni.springbootintroduction.services.BrandService;
import bg.softuni.springbootintroduction.services.OfferService;
import bg.softuni.springbootintroduction.services.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

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

    @ModelAttribute("offerDetails")
    public OfferDetailsViewModel initOfferDetailsModel() {
        return new OfferDetailsViewModel();
    }

    @ModelAttribute("offerUpdate")
    public OfferUpdateBindingModel initOfferUpdateModel() {
        return new OfferUpdateBindingModel();
    }

    @GetMapping("/add")
    public String showAddOffer(Model model) {
        model.addAttribute("brands", this.brandService.getAllBrands());

        return "offer-add";
    }

    @PostMapping("/add")
    public String addOffer(@Valid OfferSubmitBindingModel offerSubmitBindingModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           Principal principal) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("offer", offerSubmitBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.offer",
                    bindingResult);

            return "redirect:/offers/add";
        }

        this.offerService.addOffer(offerSubmitBindingModel, principal);

        return "redirect:/";
    }

    @GetMapping("/all")
    public String showAllOffers(Model model) {
        model.addAttribute("offers", this.offerService.getAllOffers());

        return "offers";
    }

    @GetMapping("/details/{offerId}")
    public String showDetails(@PathVariable Long offerId, Model model) {
        OfferDetailsViewModel offerDetails = this.offerService.getOfferDetailsModelById(offerId).get();

        String principalName = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("offerDetails", offerDetails);
        model.addAttribute("canModify", this.offerService.isOwner(principalName, offerId));

        return "details";
    }

    @PreAuthorize("@offerServiceImpl.isOwner(#principal.name, #id)")
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Principal principal) {
        this.offerService.deleteOfferById(id);

        return "redirect:/offers/all";
    }

    @GetMapping("/update/{id}/errors")
    public String updateErrors(@PathVariable Long id) {
        return "update";
    }

    @PreAuthorize("@offerServiceImpl.isOwner(#principal.name, #id)")
    @GetMapping("/update/{id}")
    public String showUpdate(@PathVariable Long id, Model model, Principal principal) {
        OfferUpdateBindingModel offerUpdate = this.offerService.getOfferUpdateModelById(id).get();

        model.addAttribute("offerUpdate", offerUpdate);

        return "update";
    }

    @PreAuthorize("@offerServiceImpl.isOwner(#principal.name, #id)")
    @PatchMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         @Valid OfferUpdateBindingModel offerUpdateBindingModel,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Principal principal) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("offerUpdate", offerUpdateBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.offerUpdate",
                    bindingResult);

            return "redirect:/offers/update/" + id + "/errors";
        }

        this.offerService.updateOffer(offerUpdateBindingModel);

        return "redirect:/offers/all";
    }
}
