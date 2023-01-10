package bg.softuni.springbootintroduction.web;

import bg.softuni.springbootintroduction.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/brands")
public class BrandController {

    private static final String ATTRIBUTE_NAME = "brands";

    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/all")
    public String showBrands(Model model) {
        model.addAttribute(ATTRIBUTE_NAME, brandService.getAllBrands());

        return "brands";
    }
}
