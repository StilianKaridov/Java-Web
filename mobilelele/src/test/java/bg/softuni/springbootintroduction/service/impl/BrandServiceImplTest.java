package bg.softuni.springbootintroduction.service.impl;

import bg.softuni.springbootintroduction.domain.entity.Brand;
import bg.softuni.springbootintroduction.domain.view.BrandViewModel;
import bg.softuni.springbootintroduction.repository.BrandRepository;
import bg.softuni.springbootintroduction.service.BrandService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BrandServiceImplTest {

    @Mock
    private BrandRepository mockedBrandRepository;

    private BrandService brandService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ModelMapper mapper = new ModelMapper();

        this.brandService = new BrandServiceImpl(mockedBrandRepository, mapper);
    }

    @Test
    void test_GetAllBrands_ShouldReturn_Two_Brands() {
        Brand brand1 = new Brand("brand1", Instant.now(), Instant.now(), new HashSet<>());
        Brand brand2 = new Brand("brand2", Instant.now(), Instant.now(), new HashSet<>());

        List<Brand> preparedBrands = List.of(brand1, brand2);

        when(mockedBrandRepository.findAll()).thenReturn(preparedBrands);

        List<BrandViewModel> allBrands = this.brandService.getAllBrands();

        assertEquals(preparedBrands.size(), allBrands.size());
    }
}