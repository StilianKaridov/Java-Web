package bg.softuni.springbootintroduction;

import bg.softuni.springbootintroduction.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final BrandService brandService;
    private final ModelService modelService;
    private final OfferService offerService;
    private final UserRoleService userRoleService;
    private final UserService userService;

    @Autowired
    public DataInitializer(BrandService brandService, ModelService modelService, OfferService offerService, UserRoleService userRoleService, UserService userService) {
        this.brandService = brandService;
        this.modelService = modelService;
        this.offerService = offerService;
        this.userRoleService = userRoleService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.brandService.seedBrands();
        this.modelService.seedModels();
        this.userRoleService.seedUserRoles();
        this.userService.seedUsers();
        this.offerService.seedOffers();
    }
}
