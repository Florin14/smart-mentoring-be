package ro.ubb.mp.controller;

import ro.ubb.mp.controller.dto.mapper.*;
import ro.ubb.mp.controller.dto.request.ProductRequestDTO;
import ro.ubb.mp.controller.dto.request.ProductSizeStockRequestDTO;
import ro.ubb.mp.controller.dto.response.ResponseWrapperDTO;
import ro.ubb.mp.controller.dto.response.brand.BrandResponseDTO;
import ro.ubb.mp.controller.dto.response.category.CategoryResponseDTO;
import ro.ubb.mp.controller.dto.response.gender.GenderResponseDTO;
import ro.ubb.mp.controller.dto.response.product.ProductResourcesResponseDTO;
import ro.ubb.mp.controller.dto.response.product.ProductResponseDTO;
import ro.ubb.mp.controller.dto.response.product_size.ProductSizeResponseDTO;
import ro.ubb.mp.dao.model.*;
import ro.ubb.mp.service.brand.BrandService;
import ro.ubb.mp.service.category.CategoryService;
import ro.ubb.mp.service.gender.GenderService;
import ro.ubb.mp.service.product.ProductService;
import ro.ubb.mp.service.product_size.ProductSizeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
@Getter
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final GenderService genderService;
    private final ProductSizeService productSizeService;
    private final ProductSizeMapper productSizeMapper;
    private final ProductMapper productMapper;
    private final BrandMapper brandMapper;
    private final CategoryMapper categoryMapper;
    private final GenderMapper genderMapper;
    private final ProductImageMapper productImageMapper;

    @GetMapping()
    public ResponseEntity<ResponseWrapperDTO<List<ProductResponseDTO>>> getProducts(@RequestParam(required = false) String sortBy,
                                                                                    @RequestParam(required = false) String sortType
    ) {
        List<Product> products = productService.getAll();

        // Sort the products if sortBy and sortType are provided
        if (sortBy != null && sortType != null) {
            Comparator<Product> comparator = getComparator(sortBy, sortType);
            products.sort(comparator);
        }
        List<ProductResponseDTO> productResponseDTOS = products.stream()
                .map(product -> {
                    ProductResponseDTO productResponseDTO = getProductMapper().toDTO(product);
                    productResponseDTO.setImages(
                            product.getImages().stream()
                                    .map(productImageMapper::toDTO)
                                    .collect(Collectors.toList())
                    );
                    return productResponseDTO;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok()
                .body(ResponseWrapperDTO.<List<ProductResponseDTO>>builder().data(productResponseDTOS).build());
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseWrapperDTO<ProductResponseDTO>> addProduct(final @ModelAttribute ProductRequestDTO product) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductSizeStockRequestDTO> productStock = objectMapper.readValue(product.getProduct_stock(), new TypeReference<List<ProductSizeStockRequestDTO>>() {
        });
        URI uri = URI.create((ServletUriComponentsBuilder.fromCurrentContextPath().path("/addProduct").toUriString()));

        return ResponseEntity.created(uri).body(ResponseWrapperDTO.<ProductResponseDTO>builder()
                .data(getProductMapper().toDTO(getProductService().saveProduct(product, productStock))).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapperDTO<ProductResponseDTO>> getAppointmentById(@PathVariable Long id) {


        Product product = getProductService().findById(id).orElseThrow(EntityNotFoundException::new);
        ProductResponseDTO productResponseDTO = productMapper.toDTO(product);


        return ResponseEntity.ok()
                .body(ResponseWrapperDTO.<ProductResponseDTO>builder().data(productResponseDTO).build());

    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapperDTO<ProductResponseDTO>> updateProduct(@RequestBody ProductRequestDTO product,
                                                                                @PathVariable Long id) {
        return ResponseEntity.ok().body(ResponseWrapperDTO.<ProductResponseDTO>builder().data(getProductMapper().toDTO(getProductService().
                updateProduct(product, id))).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteProduct(@PathVariable Long id) {

        final Product product = getProductService().findById(id).
                orElseThrow(EntityNotFoundException::new);
        productService.deleteProductById(product.getId());
        return new ResponseEntity<>(id, HttpStatus.OK);

    }

    @GetMapping("/resources")
    public ResponseEntity<ProductResourcesResponseDTO> getProductsResources() {
        List<Brand> brands = brandService.getAll();
        List<Category> categories = categoryService.getAll();
        List<Gender> genders = genderService.getAll();
        List<ProductSize> productSizes = productSizeService.getAll();
        List<BrandResponseDTO> brandResponseDTOS = brands.stream()
                .map(brand -> getBrandMapper().toDTO(brand)).toList();
        List<CategoryResponseDTO> categoriesResponseDTOS = categories.stream()
                .map(category -> getCategoryMapper().toDTO(category)).toList();
        List<GenderResponseDTO> gendersResponseDTOS = genders.stream()
                .map(gender -> getGenderMapper().toDTO(gender)).toList();
        List<ProductSizeResponseDTO> productSizesResponseDTOS = productSizes.stream()
                .map(productSize -> getProductSizeMapper().toDTO(productSize)).toList();
        ProductResourcesResponseDTO responseDTO = ProductResourcesResponseDTO.builder()
                .brands(brandResponseDTOS)
                .categories(categoriesResponseDTOS)
                .genders(gendersResponseDTOS)
                .productSizes(productSizesResponseDTOS)
                .build();

        return ResponseEntity.ok().body(responseDTO);
    }

    private Comparator<Product> getComparator(String sortBy, String sortType) {
        Comparator<Product> comparator = null;
        switch (sortBy) {
            case "name":
                comparator = Comparator.comparing(Product::getName);
                break;
            case "price":
                comparator = Comparator.comparing(Product::getPrice);
                break;
            case "date":
                comparator = Comparator.comparing(Product::getDate);
                break;
            default:
                comparator = Comparator.comparing(Product::getId);
                break;
        }
        // If sortType is "desc", reverse the comparator
        if (sortType.equalsIgnoreCase("desc")) {
            comparator = comparator.reversed();
        }
        return comparator;
    }
}


