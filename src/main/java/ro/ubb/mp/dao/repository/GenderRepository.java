package ro.ubb.mp.dao.repository;

import ro.ubb.mp.dao.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenderRepository extends JpaRepository<Gender, Long> {

}
