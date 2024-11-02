package ro.ubb.mp.dao.repository;

import ro.ubb.mp.dao.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
