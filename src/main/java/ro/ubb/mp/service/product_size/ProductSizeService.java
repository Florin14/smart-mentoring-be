package ro.ubb.mp.service.product_size;

import ro.ubb.mp.controller.dto.request.ProductSizeRequestDTO;
import ro.ubb.mp.dao.model.ProductSize;

import java.util.List;
import java.util.Optional;

public interface ProductSizeService {
    Optional<ProductSize> findById(Long id);

    List<ProductSize> getAll();

    ProductSize saveProductSize(ProductSizeRequestDTO productSizeRequestDTO);

    ProductSize updateProductSize(ProductSizeRequestDTO productSizeRequestDTO, Long id);

    void deleteProductSizeById(Long id);

}
