package swp391.SPS.services;

//import swp391.SPS.entities.Accessory;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.repository.query.Param;
import swp391.SPS.entities.Order;
import swp391.SPS.entities.Phone;
import swp391.SPS.exceptions.FileNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    List<Order> getAllOrder();
    List<Order> ListOrderByUserId(int id);
    Order getOrder(int oId) throws FileNotFoundException;
    void placeOrder(String userName);
    void cancelOrder(int orderId);
    List<Order> searchOrderByUserName(String name);
    void updateOrderStatus(int id, String status);
    List<Order> findOrdersBetweenDates(LocalDate startDate,LocalDate endDate);
}
