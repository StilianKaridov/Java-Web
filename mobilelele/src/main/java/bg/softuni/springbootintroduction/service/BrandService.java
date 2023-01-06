package bg.softuni.springbootintroduction.service;

import bg.softuni.springbootintroduction.domain.view.BrandViewModel;

import java.util.List;

public interface BrandService {

    void seedBrands();

    List<BrandViewModel> getAllBrands();
}
