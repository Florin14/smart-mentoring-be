package ro.ubb.mp.service.brand;

import ro.ubb.mp.controller.dto.request.BrandRequestDTO;
import ro.ubb.mp.dao.model.Brand;

import java.util.List;
import java.util.Optional;

public interface BrandService {
    Optional<Brand> findById(Long id);

    List<Brand> getAll();

    Brand saveBrand(BrandRequestDTO brandRequestDTO);

    Brand updateBrand(BrandRequestDTO brandRequestDTO, Long id);

    void deleteBrandById(Long id);

}
