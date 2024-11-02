package ro.ubb.mp.service.product;

import ro.ubb.mp.controller.dto.request.ProductRequestDTO;
import ro.ubb.mp.controller.dto.request.ProductSizeStockRequestDTO;
import ro.ubb.mp.dao.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<Product> findById(Long id);

    List<Product> getAll();

    Product saveProduct(ProductRequestDTO productRequestDTO, List<ProductSizeStockRequestDTO> productStock);

    Product updateProduct(ProductRequestDTO productRequestDTO, Long id);

    void deleteProductById(Long id);

}
