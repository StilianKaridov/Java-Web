package bg.softuni.springbootintroduction.service.impl;

import bg.softuni.springbootintroduction.domain.dto.ModelGetManufacturingYearsDTO;
import bg.softuni.springbootintroduction.domain.dto.ModelImportDTO;
import bg.softuni.springbootintroduction.domain.entity.Brand;
import bg.softuni.springbootintroduction.domain.entity.Model;
import bg.softuni.springbootintroduction.repository.BrandRepository;
import bg.softuni.springbootintroduction.repository.ModelRepository;
import bg.softuni.springbootintroduction.service.ModelService;
import bg.softuni.springbootintroduction.utils.enums.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelServiceImpl implements ModelService {

    private final ModelRepository modelRepository;
    private final BrandRepository brandRepository;
    private final ModelMapper mapper;

    @Autowired
    public ModelServiceImpl(ModelRepository modelRepository, BrandRepository brandRepository, ModelMapper mapper) {
        this.modelRepository = modelRepository;
        this.brandRepository = brandRepository;
        this.mapper = mapper;
    }

    @Override
    public void seedModels() {
        if (this.modelRepository.count() == 0) {
            ModelImportDTO model1 = new ModelImportDTO("CLS-Class", Category.Car, 2011, "Mercedes-Benz");
            ModelImportDTO model2 = new ModelImportDTO("A4", Category.Car, 2008, "Audi");

            Model toInsert1 = this.mapper.map(model1, Model.class);
            Model toInsert2 = this.mapper.map(model2, Model.class);

            Brand brand1 = this.brandRepository.getBrandByName(model1.getBrand());
            Brand brand2 = this.brandRepository.getBrandByName(model2.getBrand());

            toInsert1.setBrand(brand1);
            toInsert2.setBrand(brand2);

            this.modelRepository.saveAndFlush(toInsert1);
            this.modelRepository.saveAndFlush(toInsert2);
        }
    }

    @Override
    public ModelGetManufacturingYearsDTO getModelWithManufacturingYears(String modelName) {
        Model model = this.modelRepository.findFirstByName(modelName);

        return this.mapper.map(model, ModelGetManufacturingYearsDTO.class);
    }
}
