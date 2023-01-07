package bg.softuni.springbootintroduction.service.impl;

import bg.softuni.springbootintroduction.domain.dto.ModelGetManufacturingYearsDTO;
import bg.softuni.springbootintroduction.domain.entity.Brand;
import bg.softuni.springbootintroduction.domain.entity.Model;
import bg.softuni.springbootintroduction.repository.BrandRepository;
import bg.softuni.springbootintroduction.repository.ModelRepository;
import bg.softuni.springbootintroduction.service.ModelService;
import bg.softuni.springbootintroduction.utils.enums.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ModelServiceImplTest {

    private static final String INVALID_MODEL_NAME = "INVALID_MODEL_NAME";

    private static final String BRAND_NAME = "Audi";

    private static final String MODEL_NAME = "A4";

    private static final int MODEL_START_YEAR = 2008;

    private static final int MODEL_END_YEAR = 2014;

    private static final Category MODEL_CATEGORY = Category.Car;

    @Mock
    private ModelRepository mockedModelRepository;

    @Mock
    private BrandRepository mockedBrandRepository;

    private ModelService modelService;

    private Model modelEntity;

    @BeforeEach
    public void setUp() {
        ModelMapper mapper = new ModelMapper();

        this.modelService = new ModelServiceImpl(
                mockedModelRepository,
                mockedBrandRepository,
                mapper
        );

        Brand brandEntity = new Brand();
        brandEntity.setName(BRAND_NAME);

        this.modelEntity = new Model();
        modelEntity.setName(MODEL_NAME);
        modelEntity.setBrand(brandEntity);
        modelEntity.setStartYear(MODEL_START_YEAR);
        modelEntity.setEndYear(MODEL_END_YEAR);
        modelEntity.setCategory(MODEL_CATEGORY);
    }

    @Test
    public void test_FindModelByName_ModelExists() {
        when(
                this.mockedModelRepository
                        .findFirstByName(this.modelEntity.getName())
        ).thenReturn(this.modelEntity);

        Model toTest = this.mockedModelRepository
                .findFirstByName(this.modelEntity.getName());

        assertEquals(this.modelEntity.getName(), toTest.getName());
        assertEquals(this.modelEntity.getBrand().getName(), toTest.getBrand().getName());
        assertEquals(this.modelEntity.getStartYear(), toTest.getStartYear());
        assertEquals(this.modelEntity.getEndYear(), toTest.getEndYear());
        assertEquals(this.modelEntity.getCategory(), toTest.getCategory());
    }

    @Test
    public void test_FindModelByName_ModelDoesNotExist() {
        when(this.mockedModelRepository
                .findFirstByName(INVALID_MODEL_NAME)
        ).thenReturn(null);

        Model toTest = this.mockedModelRepository
                .findFirstByName(INVALID_MODEL_NAME);

        assertNull(toTest);
    }

    @Test
    public void test_GetModelWithManufacturingYears_ReturnsProperModel() {
        when(
                this.mockedModelRepository
                        .findFirstByName(this.modelEntity.getName())
        ).thenReturn(this.modelEntity);

        ModelGetManufacturingYearsDTO toTest = this.modelService
                .getModelWithManufacturingYears(
                        this.modelEntity.getName()
                );

        assertEquals(this.modelEntity.getName(), toTest.getName());
        assertEquals(this.modelEntity.getStartYear(), toTest.getStartYear());
        assertEquals(this.modelEntity.getEndYear(), toTest.getEndYear());
    }
}