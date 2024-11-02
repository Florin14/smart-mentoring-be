package ro.ubb.mp.service.order;

import ro.ubb.mp.controller.dto.request.OrderRequestDTO;
import ro.ubb.mp.dao.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Optional<Order> findById(Long id);

    List<Order> getAll();

    Order saveOrder(OrderRequestDTO orderRequestDTO);

    Order updateOrder(OrderRequestDTO orderRequestDTO, Long id);

    void deleteOrderById(Long id);

}
