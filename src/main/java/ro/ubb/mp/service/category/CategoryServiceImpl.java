package ro.ubb.mp.service.category;

import ro.ubb.mp.controller.dto.request.CategoryRequestDTO;
import ro.ubb.mp.dao.model.Category;
import ro.ubb.mp.dao.repository.CategoryRepository;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Data
@Getter
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category saveCategory(CategoryRequestDTO categoryDTO) {
        final Category productToBeSaved = Category.builder()
                .name(categoryDTO.getName())
                .build();
        return categoryRepository.save(productToBeSaved);

    }

    @Override
    public Category updateCategory(CategoryRequestDTO categoryDTO, Long id) {
        Category category = findById(id).orElseThrow(EntityNotFoundException::new);

        category.setName(categoryDTO.getName());

        return categoryRepository.save(category);

    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }
}
