package ro.ubb.mp.service.brand;

import ro.ubb.mp.controller.dto.request.BrandRequestDTO;
import ro.ubb.mp.dao.model.Brand;
import ro.ubb.mp.dao.repository.BrandRepository;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Data
@Getter
@Service
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;

    @Override
    public Optional<Brand> findById(Long id) {
        return brandRepository.findById(id);
    }

    @Override
    public List<Brand> getAll() {
        return brandRepository.findAll();
    }

    @Override
    public Brand saveBrand(BrandRequestDTO brandDTO) {
        final Brand brandToBeSaved = Brand.builder()
                .name(brandDTO.getName())

                .build();
        return brandRepository.save(brandToBeSaved);

    }

    @Override
    public Brand updateBrand(BrandRequestDTO brandDTO, Long id) {
        Brand brand = findById(id).orElseThrow(EntityNotFoundException::new);

        brand.setName(brandDTO.getName());

        return brandRepository.save(brand);

    }

    @Override
    public void deleteBrandById(Long id) {
        brandRepository.deleteById(id);
    }
}
