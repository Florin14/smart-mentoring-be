package ro.ubb.mp.controller;

import ro.ubb.mp.controller.dto.mapper.BrandMapper;
import ro.ubb.mp.controller.dto.request.BrandRequestDTO;
import ro.ubb.mp.controller.dto.response.brand.BrandResponseDTO;
import ro.ubb.mp.dao.model.Brand;
import ro.ubb.mp.service.brand.BrandService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/brands")
@Getter
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;
    private final BrandMapper brandMapper;

    @GetMapping()
    public ResponseEntity<List<BrandResponseDTO>> getBrands() {
        List<Brand> brands = brandService.getAll();
        List<BrandResponseDTO> brandResponseDTOS = brands.stream()
                .map(brand -> getBrandMapper().toDTO(brand)).collect(Collectors.toList());

        return ResponseEntity.ok().body(brandResponseDTOS);
    }

    @PostMapping()
    public ResponseEntity<BrandResponseDTO> addBrand(@RequestBody BrandRequestDTO brand) {
        URI uri = URI.create((ServletUriComponentsBuilder.fromCurrentContextPath().path("/addBrand").toUriString()));
        return ResponseEntity.created(uri).body(getBrandMapper().toDTO(getBrandService().saveBrand(brand)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BrandResponseDTO> updateBrand(@RequestBody BrandRequestDTO brand,
                                                        @PathVariable Long id) {
        return ResponseEntity.ok().body(getBrandMapper().toDTO(getBrandService().
                updateBrand(brand, id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteBrand(@PathVariable Long id) {

        final Brand brand = getBrandService().findById(id).
                orElseThrow(EntityNotFoundException::new);
        brandService.deleteBrandById(brand.getId());
        return new ResponseEntity<>(id, HttpStatus.OK);

    }
}
