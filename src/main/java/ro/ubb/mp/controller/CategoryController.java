package ro.ubb.mp.controller;

import ro.ubb.mp.controller.dto.mapper.CategoryMapper;
import ro.ubb.mp.controller.dto.request.CategoryRequestDTO;
import ro.ubb.mp.controller.dto.response.category.CategoryResponseDTO;
import ro.ubb.mp.dao.model.Category;
import ro.ubb.mp.service.category.CategoryService;
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
@RequestMapping("/categories")
@Getter
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping()
    public ResponseEntity<List<CategoryResponseDTO>> getCategories() {
        List<Category> products = categoryService.getAll();
        List<CategoryResponseDTO> categoryResponseDTOS = products.stream()
                .map(category -> getCategoryMapper().toDTO(category)).collect(Collectors.toList());

        return ResponseEntity.ok().body(categoryResponseDTOS);
    }

    @PostMapping()
    public ResponseEntity<CategoryResponseDTO> addCategory(@RequestBody CategoryRequestDTO category) {
        URI uri = URI.create((ServletUriComponentsBuilder.fromCurrentContextPath().path("/addCategory").toUriString()));
        return ResponseEntity.created(uri).body(getCategoryMapper().toDTO(getCategoryService().saveCategory(category)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@RequestBody CategoryRequestDTO category,
                                                              @PathVariable Long id) {
        return ResponseEntity.ok().body(getCategoryMapper().toDTO(getCategoryService().
                updateCategory(category, id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteCategory(@PathVariable Long id) {

        final Category category = getCategoryService().findById(id).
                orElseThrow(EntityNotFoundException::new);
        categoryService.deleteCategoryById(category.getId());
        return new ResponseEntity<>(id, HttpStatus.OK);

    }
}
