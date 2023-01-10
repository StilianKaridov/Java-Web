package bg.softuni.springbootintroduction.web;

import bg.softuni.springbootintroduction.domain.binding.OfferSubmitBindingModel;
import bg.softuni.springbootintroduction.domain.binding.OfferUpdateBindingModel;
import bg.softuni.springbootintroduction.domain.view.OfferDetailsViewModel;
import bg.softuni.springbootintroduction.service.BrandService;
import bg.softuni.springbootintroduction.service.OfferService;
import bg.softuni.springbootintroduction.service.exceptions.OfferNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/offers")
public class OfferController {

    private static final String ATTRIBUTE_CAN_MODIFY_LABEL = "canModify";
    private static final String ATTRIBUTE_OFFER_ID_LABEL = "offerId";

    private static final String BRANDS_LABEL = "brands";
    private static final String OFFERS_LABEL = "offers";

    private static final String OFFER_MODEL_LABEL = "offer";
    private static final String OFFER_MODEL_BINDING_RESULT_LABEL = "org.springframework.validation.BindingResult.offer";

    private static final String OFFER_DETAILS_MODEL_LABEL = "offerDetails";

    private static final String OFFER_UPDATE_MODEL_LABEL = "offerUpdate";
    private static final String OFFER_UPDATE_BINDING_RESULT_LABEL = "org.springframework.validation.BindingResult.offerUpdate";


    private final OfferService offerService;
    private final BrandService brandService;

    @Autowired
    public OfferController(OfferService offerService, BrandService brandService) {
        this.offerService = offerService;
        this.brandService = brandService;
    }

    @ModelAttribute(OFFER_MODEL_LABEL)
    public OfferSubmitBindingModel initOfferModel() {
        return new OfferSubmitBindingModel();
    }

    @ModelAttribute(OFFER_DETAILS_MODEL_LABEL)
    public OfferDetailsViewModel initOfferDetailsModel() {
        return new OfferDetailsViewModel();
    }

    @ModelAttribute(OFFER_UPDATE_MODEL_LABEL)
    public OfferUpdateBindingModel initOfferUpdateModel() {
        return new OfferUpdateBindingModel();
    }

    @GetMapping("/add")
    public String showAddOffer(Model model) {
        model.addAttribute(BRANDS_LABEL, this.brandService.getAllBrands());

        return "offer-add";
    }

    @PostMapping("/add")
    public String addOffer(@Valid OfferSubmitBindingModel offerSubmitBindingModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           Principal principal) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(OFFER_MODEL_LABEL, offerSubmitBindingModel);
            redirectAttributes.addFlashAttribute(OFFER_MODEL_BINDING_RESULT_LABEL, bindingResult);

            return "redirect:/offers/add";
        }

        this.offerService.addOffer(offerSubmitBindingModel, principal);

        return "redirect:/";
    }

    @GetMapping("/all")
    public String showAllOffers(Model model) {
        model.addAttribute(OFFERS_LABEL, this.offerService.getAllOffers());

        return "offers";
    }

    @GetMapping("/details/{offerId}")
    public String showDetails(@PathVariable Long offerId, Model model) {
        OfferDetailsViewModel offerDetails = this.offerService.getOfferDetailsModelById(offerId).get();

        String principalName = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute(OFFER_DETAILS_MODEL_LABEL, offerDetails);
        model.addAttribute(ATTRIBUTE_CAN_MODIFY_LABEL, this.offerService.isOwner(principalName, offerId));

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

        model.addAttribute(OFFER_UPDATE_MODEL_LABEL, offerUpdate);

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
            redirectAttributes.addFlashAttribute(OFFER_UPDATE_MODEL_LABEL, offerUpdateBindingModel);
            redirectAttributes.addFlashAttribute(OFFER_UPDATE_BINDING_RESULT_LABEL, bindingResult);

            return "redirect:/offers/update/" + id + "/errors";
        }

        this.offerService.updateOffer(offerUpdateBindingModel);

        return "redirect:/offers/all";
    }

    @ExceptionHandler(OfferNotFoundException.class)
    public ModelAndView handleOfferException(OfferNotFoundException e){
        ModelAndView modelAndView = new ModelAndView("offer-not-found");
        modelAndView.addObject(ATTRIBUTE_OFFER_ID_LABEL, e.getId());
        modelAndView.setStatus(e.getClass().getAnnotation(ResponseStatus.class).value());

        return modelAndView;
    }
}
