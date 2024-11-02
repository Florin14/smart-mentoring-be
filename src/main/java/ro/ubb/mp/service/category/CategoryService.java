package ro.ubb.mp.service.category;

import ro.ubb.mp.controller.dto.request.CategoryRequestDTO;
import ro.ubb.mp.dao.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Optional<Category> findById(Long id);

    List<Category> getAll();

    Category saveCategory(CategoryRequestDTO categoryRequestDTO);

    Category updateCategory(CategoryRequestDTO categoryRequestDTO, Long id);

    void deleteCategoryById(Long id);

}
