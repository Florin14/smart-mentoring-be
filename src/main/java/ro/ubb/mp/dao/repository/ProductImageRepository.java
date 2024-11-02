package ro.ubb.mp.dao.repository;

import ro.ubb.mp.dao.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}