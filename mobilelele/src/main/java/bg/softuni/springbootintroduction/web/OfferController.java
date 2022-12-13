package bg.softuni.springbootintroduction.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/offers")
public class OfferController {

    @GetMapping("/add")
    public String showAddOffer() {
        return "offer-add";

        //Model +
        //Price
        //Engine +
        //Transmission +
        //Year
        //Mileage
        //Description
        //ImageUrl
    }

    @GetMapping("/all")
    public String showAllOffers() {
        return "offers";
    }
}
