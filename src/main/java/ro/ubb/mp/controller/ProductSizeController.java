package ro.ubb.mp.controller;

import ro.ubb.mp.controller.dto.mapper.ProductSizeMapper;
import ro.ubb.mp.controller.dto.request.ProductSizeRequestDTO;
import ro.ubb.mp.controller.dto.response.product_size.ProductSizeResponseDTO;
import ro.ubb.mp.dao.model.ProductSize;
import ro.ubb.mp.service.product_size.ProductSizeService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product-sizes")
@Getter
@RequiredArgsConstructor
public class ProductSizeController {

    private final ProductSizeService productSizeService;
    private final ProductSizeMapper productSizeMapper;

    @GetMapping()
    public ResponseEntity<List<ProductSizeResponseDTO>> getProductSizes() {
        List<ProductSize> productSizes = productSizeService.getAll();
//        List<ProductSizeResponseDTO> productSizeResponseDTOS1 = productSizes.stream()
//                .map(brand -> getProductSizeMapper().toDTO(brand)).collect(Collectors.toList());

        List<ProductSizeResponseDTO> productSizeResponseDTOS = productSizes.stream()
                .map(productSize -> getProductSizeMapper().toDTO(productSize))
                .sorted(Comparator
                        .comparing((ProductSizeResponseDTO ps) -> !ps.isForShoes() && !ps.isForKids())
                        .thenComparing(ProductSizeResponseDTO::getName)
                )
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(productSizeResponseDTOS);
    }

    @PostMapping()
    public ResponseEntity<ProductSizeResponseDTO> addProductSize(@RequestBody ProductSizeRequestDTO productSize) {
        URI uri = URI.create((ServletUriComponentsBuilder.fromCurrentContextPath().path("/addProductSize").toUriString()));
        return ResponseEntity.created(uri).body(getProductSizeMapper().toDTO(getProductSizeService().saveProductSize(productSize)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductSizeResponseDTO> updateProductSize(@RequestBody ProductSizeRequestDTO productSize,
                                                              @PathVariable Long id) {
        return ResponseEntity.ok().body(getProductSizeMapper().toDTO(getProductSizeService().
                updateProductSize(productSize, id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteProductSize(@PathVariable Long id) {

        final ProductSize productSize = getProductSizeService().findById(id).
                orElseThrow(EntityNotFoundException::new);
        productSizeService.deleteProductSizeById(productSize.getId());
        return new ResponseEntity<>(id, HttpStatus.OK);

    }
}
