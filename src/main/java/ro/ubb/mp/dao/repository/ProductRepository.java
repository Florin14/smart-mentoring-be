package ro.ubb.mp.dao.repository;

import ro.ubb.mp.dao.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
