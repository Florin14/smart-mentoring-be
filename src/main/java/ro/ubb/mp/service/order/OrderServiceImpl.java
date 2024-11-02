package ro.ubb.mp.service.order;

import ro.ubb.mp.controller.dto.request.OrderRequestDTO;
import ro.ubb.mp.dao.model.Order;
import ro.ubb.mp.dao.repository.OrderRepository;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Data
@Getter
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
//    private final UserRepository userRepository;

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order saveOrder(OrderRequestDTO orderDTO) {
        final Order orderToBeSaved = Order.builder()
                .orderDate(LocalDateTime.now())
                .build();

        if(orderDTO.getUserId() != null) {
//            final User user = getUserRepository().findById(orderDTO.getUserId()).orElseThrow(EntityNotFoundException::new);
//            orderToBeSaved.setUser(user);
        }
        return orderRepository.save(orderToBeSaved);

    }

    @Override
    public Order updateOrder(OrderRequestDTO orderDTO, Long id) {
        Order order = findById(id).orElseThrow(EntityNotFoundException::new);

        return orderRepository.save(order);

    }

    @Override
    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }
}
