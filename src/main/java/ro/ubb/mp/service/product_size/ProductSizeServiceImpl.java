package ro.ubb.mp.service.product_size;

import ro.ubb.mp.controller.dto.request.ProductSizeRequestDTO;
import ro.ubb.mp.dao.model.ProductSize;
import ro.ubb.mp.dao.repository.ProductSizeRepository;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Data
@Getter
@Service
public class ProductSizeServiceImpl implements ProductSizeService {
    private final ProductSizeRepository productSizeRepository;

    @Override
    public Optional<ProductSize> findById(Long id) {
        return productSizeRepository.findById(id);
    }

    @Override
    public List<ProductSize> getAll() {
        return productSizeRepository.findAll();
    }

    @Override
    public ProductSize saveProductSize(ProductSizeRequestDTO productSizeDTO) {
        final ProductSize genderToBeSaved = ProductSize.builder()
                .name(productSizeDTO.getName())
                .isForShoes(productSizeDTO.isForShoes())
                .isForKids(productSizeDTO.isForKids())
                .build();
        return productSizeRepository.save(genderToBeSaved);

    }

    @Override
    public ProductSize updateProductSize(ProductSizeRequestDTO genderDTO, Long id) {
        ProductSize productSize = findById(id).orElseThrow(EntityNotFoundException::new);

        productSize.setName(genderDTO.getName());
        productSize.setForShoes(genderDTO.isForShoes());
        productSize.setForKids(genderDTO.isForKids());

        return productSizeRepository.save(productSize);

    }

    @Override
    public void deleteProductSizeById(Long id) {
        productSizeRepository.deleteById(id);
    }
}
