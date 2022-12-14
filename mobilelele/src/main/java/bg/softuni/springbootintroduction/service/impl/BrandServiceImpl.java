package bg.softuni.springbootintroduction.service.impl;

import bg.softuni.springbootintroduction.domain.dto.BrandImportDTO;
import bg.softuni.springbootintroduction.domain.entity.Brand;
import bg.softuni.springbootintroduction.domain.view.BrandViewModel;
import bg.softuni.springbootintroduction.repository.BrandRepository;
import bg.softuni.springbootintroduction.service.BrandService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    private static final String BRAND_MERCEDES_NAME = "Mercedes-Benz";
    private static final String BRAND_AUDI_NAME = "Audi";

    private final BrandRepository brandRepository;
    private final ModelMapper mapper;

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository, ModelMapper mapper) {
        this.brandRepository = brandRepository;
        this.mapper = mapper;
    }

    @Override
    public void seedBrands() {
        if (this.brandRepository.count() == 0) {
            BrandImportDTO brand = new BrandImportDTO(BRAND_MERCEDES_NAME, Instant.now(), Instant.now());
            BrandImportDTO brand2 = new BrandImportDTO(BRAND_AUDI_NAME, Instant.now(), Instant.now());

            Brand toInsert = this.mapper.map(brand, Brand.class);
            Brand toInsert2 = this.mapper.map(brand2, Brand.class);

            this.brandRepository.saveAndFlush(toInsert);
            this.brandRepository.saveAndFlush(toInsert2);
        }

    }

    @Override
    public List<BrandViewModel> getAllBrands() {
        List<Brand> brands = this.brandRepository.findAll();

        List<BrandViewModel> toReturn = new ArrayList<>();

        for (Brand b : brands) {
            BrandViewModel mappedBrand = mapper.map(b, BrandViewModel.class);

            toReturn.add(mappedBrand);
        }

        return toReturn;
    }
}
