package ro.ubb.mp.dao.repository;

import ro.ubb.mp.dao.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}