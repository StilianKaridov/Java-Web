package bg.softuni.springbootintroduction.web;

import bg.softuni.springbootintroduction.services.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/brands")
public class BrandController {

    private final ModelService modelService;

    @Autowired
    public BrandController(ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping("/all")
    public String showBrands() {
        return "brands";
    }
}
