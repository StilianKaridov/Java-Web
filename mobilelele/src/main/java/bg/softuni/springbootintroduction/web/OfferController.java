package bg.softuni.springbootintroduction.web;

import bg.softuni.springbootintroduction.domain.binding.OfferSubmitBindingModel;
import bg.softuni.springbootintroduction.domain.binding.OfferUpdateBindingModel;
import bg.softuni.springbootintroduction.domain.view.OfferDetailsViewModel;
import bg.softuni.springbootintroduction.services.BrandService;
import bg.softuni.springbootintroduction.services.OfferService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/offers")
public class OfferController {

    private final OfferService offerService;
    private final BrandService brandService;
    private final ModelMapper mapper;

    @Autowired
    public OfferController(OfferService offerService, BrandService brandService, ModelMapper mapper) {
        this.offerService = offerService;
        this.brandService = brandService;
        this.mapper = mapper;
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

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        OfferDetailsViewModel offerDetails = this.offerService.getOfferDetailsModelById(id).get();


        model.addAttribute("canDelete", this.offerService.canCurrentUserModifyGivenOffer(id));
        model.addAttribute("canUpdate", this.offerService.canCurrentUserModifyGivenOffer(id));
        model.addAttribute("offerDetails", offerDetails);

        return "details";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        this.offerService.deleteOfferById(id);

        return "redirect:/offers/all";
    }

    @GetMapping("/update/{id}/errors")
    public String updateErrors(@PathVariable Long id) {
        return "update";
    }

    @GetMapping("/update/{id}")
    public String showUpdate(@PathVariable Long id, Model model) {
        OfferUpdateBindingModel offerUpdate = this.offerService.getOfferUpdateModelById(id).get();

        model.addAttribute("offerUpdate", offerUpdate);

        return "update";
    }

    @PatchMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         @Valid OfferUpdateBindingModel offerUpdateBindingModel,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

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
