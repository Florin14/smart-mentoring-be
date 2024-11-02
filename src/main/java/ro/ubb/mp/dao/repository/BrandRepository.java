package ro.ubb.mp.dao.repository;

import ro.ubb.mp.dao.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
