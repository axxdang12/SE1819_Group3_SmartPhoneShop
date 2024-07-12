package swp391.SPS.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import swp391.SPS.entities.Accessory;
import org.springframework.transaction.annotation.Transactional;
import swp391.SPS.entities.*;
import swp391.SPS.exceptions.FileNotFoundException;
import swp391.SPS.repositories.OrderItemRepository;
import swp391.SPS.repositories.OrderRepository;
import swp391.SPS.repositories.UserRepository;
import swp391.SPS.services.CartService;
import swp391.SPS.services.OrderService;
import swp391.SPS.services.UserService;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> ListOrderByUserId(int id) {
        return orderRepository.getOrderByUserId(id);
    }

    @Override
    public Order getOrder(int oId) throws FileNotFoundException {
        if (orderRepository.findById(oId).isEmpty()){
            throw new FileNotFoundException("Not found report");
        }
        return orderRepository.findById(oId).get();
    }

    @Transactional
    public void placeOrder(String userName) {
        User user = userRepository.findByUsername(userName);
        Cart cart = user.getCart();
        if (cart == null || cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }
        Order order = new Order();
        order.setUser(user);
        order.setTotalPrice(cart.getTotal());
        order.setOrderDate(LocalDate.now());
        order.setStatus("Pending");

        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setPhone(cartItem.getPhone());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotal(cartItem.getTotalPrice());
            orderItemRepository.save(orderItem);
        }
        cartService.clearCart(cart);
        orderRepository.save(order);
    }

    @Override
    public void cancelOrder(int orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order ID: " + orderId));
        order.setStatus("Canceled");
        orderRepository.save(order);
    }

    @Override
    public List<Order> searchOrderByUserName(String name) {
        return orderRepository.searchOrderByUserName(name);
    }

    @Transactional
    public void updateOrderStatus(int id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order ID: " + id));
        order.setStatus(status);
        orderRepository.save(order);
    }


}
