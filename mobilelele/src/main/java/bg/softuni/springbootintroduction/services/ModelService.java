package bg.softuni.springbootintroduction.services;

import bg.softuni.springbootintroduction.domain.dto.ModelGetManufacturingYearsDTO;

public interface ModelService {

    void seedModels();

    ModelGetManufacturingYearsDTO getModelWithManufacturingYears(String modelName);
}
