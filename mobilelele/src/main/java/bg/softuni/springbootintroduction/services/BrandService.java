package bg.softuni.springbootintroduction.services;

import bg.softuni.springbootintroduction.domain.view.BrandViewModel;

import java.util.List;

public interface BrandService {

    void seedBrands();

    List<BrandViewModel> getAllBrands();
}
