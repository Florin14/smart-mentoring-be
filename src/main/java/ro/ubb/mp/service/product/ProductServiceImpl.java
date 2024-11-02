package ro.ubb.mp.service.product;

import ro.ubb.mp.controller.dto.request.ProductRequestDTO;
import ro.ubb.mp.controller.dto.request.ProductSizeStockRequestDTO;
import ro.ubb.mp.dao.model.*;
import ro.ubb.mp.dao.repository.*;
import lombok.Data;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Data
@Getter
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final GenderRepository genderRepository;
    private final ProductImageRepository imageRepository;
    private final ProductSizeRepository productSizeRepository;
    private final ProductSizeStockRepository productSizeStockRepository;

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    @Transactional
    public Product saveProduct(ProductRequestDTO productDTO, List<ProductSizeStockRequestDTO> productStock) {
        System.out.println(productDTO);
//        try {
        Brand brand = brandRepository.findById(productDTO.getBrand_id())
                .orElseThrow(() -> new EntityNotFoundException("Brand not found with id: " + productDTO.getBrand_id()));
        Category category = categoryRepository.findById(productDTO.getCategory_id())
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + productDTO.getCategory_id()));
        Gender gender = genderRepository.findById(productDTO.getGender_id())
                .orElseThrow(() -> new EntityNotFoundException("Gender not found with id: " + productDTO.getGender_id()));

        final Product productToBeSaved = Product.builder()
                .name(productDTO.getName())
                .brand(brand)
                .gender(gender)
                .category(category)
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .sku(productDTO.getSku())
                .date(LocalDateTime.now())
                .build();

        Product savedProduct = productRepository.save(productToBeSaved);
        for (ProductSizeStockRequestDTO productSize : productStock) {
            ProductSize prodSize = productSizeRepository.findById(productSize.getSizeId())
                    .orElseThrow(() -> new EntityNotFoundException("Product size not found with id: " + productSize.getSizeId()));
            ProductSizeStock sizeStock = ProductSizeStock.builder().product(savedProduct).stockQuantity(productSize.getStockQuantity()).productSize(prodSize).build();
            productSizeStockRepository.save(sizeStock);
        }

        for (MultipartFile image : productDTO.getImages()) {
            byte[] imageData;
            try {
                imageData = image.getBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ProductImage imageEntity = ProductImage.builder().product(savedProduct).imageData(imageData).build();

            imageRepository.save(imageEntity);
        }
        return savedProduct;
//        } catch (Exception e) {
//            log.error("Error occurred while saving the product: {}", e.getMessage());
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//
//            throw new RuntimeException("Failed to save the product", e);
//        }
    }

    @Override
    public Product updateProduct(ProductRequestDTO productDTO, Long id) {
        Product product = findById(id).orElseThrow(EntityNotFoundException::new);

        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());

        return productRepository.save(product);

    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
}
