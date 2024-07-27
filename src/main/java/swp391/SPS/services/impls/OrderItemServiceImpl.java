package swp391.SPS.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp391.SPS.entities.Order;
import swp391.SPS.entities.OrderItem;
import swp391.SPS.repositories.OrderItemRepository;
import swp391.SPS.repositories.OrderRepository;
import swp391.SPS.services.OrderItemService;
import swp391.SPS.services.OrderService;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public List<OrderItem> listOrderItemByOrderId(int oId) {
        return orderItemRepository.getOrderItemByOrderId(oId);
    }

}
