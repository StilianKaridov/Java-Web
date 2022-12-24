package bg.softuni.springbootintroduction.web;

import bg.softuni.springbootintroduction.domain.dto.ModelGetManufacturingYearsDTO;
import bg.softuni.springbootintroduction.services.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OfferRestController {

    private final ModelService modelService;

    @Autowired
    public OfferRestController(ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping(value = "/offers/add/{modelName}")
    public ResponseEntity<ModelGetManufacturingYearsDTO> getModelManufacturingYears(@PathVariable String modelName) {

        ModelGetManufacturingYearsDTO model = this.modelService.getModelWithManufacturingYears(modelName);

        return ResponseEntity.ok(model);
    }
}
